package net.parchat.parchment.api.files

import com.electronwill.nightconfig.core.CommentedConfig
import com.electronwill.nightconfig.core.ConfigFormat
import com.electronwill.nightconfig.core.file.FileConfig
import com.electronwill.nightconfig.core.file.FileConfigBuilder
import com.electronwill.nightconfig.core.io.WritingMode
import com.electronwill.nightconfig.toml.TomlWriter
import net.parchat.parchment.api.files.annotations.Comment
import net.parchat.parchment.api.files.annotations.Key
import java.nio.file.Path
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * @author BillyGalbreath
 * @maintainer Ryder Belserion
 * @see <a href="https://github.com/BillyGalbreath">...</a>
 */
open class AbstractConfig {

    @OptIn(ExperimentalTime::class)
    fun reload(path: Path, classObject: Any) {
        val time = measureTime {

            val night = FileConfig.of(path)

            val file = night.file

            // Create file.
            if (!file.exists()) file.createNewFile()

            // Load the .toml file.
            night.load()

            classObject::class.java.declaredFields.forEach { method ->
                method.isAccessible = true

                val key = method.getAnnotation(Key::class.java)
                val comment = method.getAnnotation(Comment::class.java)

                val value = getValues(night, key.value, method[classObject])

                method.set(classObject, value)

                val config = CommentedConfig.inMemory()

                if (key != null) {
                    config.set("hi", "hi")
                }

                if (comment != null) {
                    config.setComment(key.value, comment.value)

                    val writer = TomlWriter()

                    writer.write(config, file, WritingMode.REPLACE)
                }
            }

            night.save()
            night.close()
        }

        println("File creation completed in ${time}")
    }

    private fun getValues(file: FileConfig, path: String, default: Any?): Any? {
        if (file.get(path) == null) file.set(path, default)

        return file.get(path)
    }
}