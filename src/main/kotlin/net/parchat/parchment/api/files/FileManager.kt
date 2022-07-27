package net.parchat.parchment.api.files

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Path

object FileManager {

    private val homeFolders = arrayListOf<String>()
    private val customFiles = arrayListOf<CustomFile>()
    private val jarHomeFolders = hashMapOf<String, String>()
    private val autoGeneratedFiles = hashMapOf<String, String>()

    private val dataFolder: File? = null

    fun setup(path: Path): FileManager {
        var filePath = path.toFile()

        if (!filePath.exists()) {
            filePath.mkdirs()

            if (dataFolder != null) filePath = dataFolder
        }

        if (isHomeFoldersEmpty()) {
            getHomeFolders().forEach { homeFolder ->
                val homeFile = File(filePath, "/$homeFolder")
                var folder = homeFolder

                if (homeFile.exists()) {
                    homeFile.list()?.forEach { name ->
                        if (name.endsWith(".yml")) {
                            val customFile = CustomFile(name, folder)

                            if (customFile.exists()) addCustomFile(customFile)
                        }
                    }

                    return this
                }

                homeFile.mkdir()

                val autoGenerated = getAutoGeneratedFiles()

                autoGenerated.keys.forEach { fileName ->
                    if (autoGenerated[fileName].equals(folder, ignoreCase = true)) {
                        folder = autoGenerated[fileName].toString()

                        runCatching {
                            val serverFile = File(filePath, "$homeFolder/$fileName")
                            val jarFile = javaClass.getResourceAsStream(jarHomeFolders.getOrDefault(fileName, homeFolder) + "/" + fileName)
                            if (jarFile != null) copyFile(jarFile, serverFile)

                            if (fileName.lowercase().endsWith(".toml")) customFiles.add(CustomFile(fileName, homeFolder))
                        }.onSuccess {
                           println("Created default file: $folder/$fileName")
                        }.onFailure {
                            println("Could not save $fileName")

                            it.printStackTrace()
                        }
                    }
                }
            }
        }

        return this
    }

    private fun copyFile(inStream: InputStream, outStream: File) {
        inStream.use { stream ->
            FileOutputStream(outStream).use { outStreams ->
                val buffer = ByteArray(4096)
                var number: Int

                while (stream.read(buffer).also { number = it } != -1) outStreams.write(buffer, 0, number)
            }
        }
    }

    private fun isHomeFoldersEmpty() = homeFolders.size > 0

    private fun getHomeFolders() = homeFolders

    private fun getAutoGeneratedFiles() = autoGeneratedFiles

    private fun getOrDefault(fileName: String, folder: String) = jarHomeFolders.getOrDefault(fileName, folder)

    fun registerCustomFolder(homeFolder: String): FileManager {
        homeFolders.add(homeFolder)

        return this
    }

    fun unregisterCustomFolder(homeFolder: String): FileManager {
        homeFolders.remove(homeFolder)

        return this
    }

    fun registerDefaultGeneratedFiles(fileName: String, homeFolder: String): FileManager {
        autoGeneratedFiles[fileName] = homeFolder
        jarHomeFolders[fileName] = homeFolder

        return this
    }

    fun unregisterDefaultGeneratedFiles(fileName: String): FileManager {
        autoGeneratedFiles.remove(fileName)
        jarHomeFolders.remove(fileName)

        return this
    }

    private fun addCustomFile(customFile: CustomFile) {
        customFiles.add(customFile)

        println("Loaded custom file: ${customFile.getHomeFolder()}/${customFile.getFileName()}!")
    }

    fun getCustom(name: String?): CustomFile? {
        for (file in customFiles) {
            if (file.getFileName().equals(name, ignoreCase = true)) {
                return file
            }
        }

        return null
    }

    fun saveCustom(fileName: String) {
        val customFile = getCustom(fileName) ?: return

        runCatching {
           customFile.getFile()?.save(File(dataFolder, "${customFile.getHomeFolder()}/${customFile.getFileName()}"))
        }.onFailure {
            println("Could not save ${customFile.getFileName()}!")

            it.printStackTrace()
        }.onSuccess {
            println("Successfully saved ${customFile.getFileName()}.")
        }
    }

    fun reloadCustom(fileName: String) {
        val customFile = getCustom(fileName) ?: return

        runCatching {
            customFile.getFile = YamlConfiguration.loadConfiguration(File(dataFolder, "/${customFile.getHomeFolder()}/${customFile.getFileName()}"))
        }.onFailure {
            println("Could not reload ${customFile.getFileName()}!")

            it.printStackTrace()
        }.onSuccess {
            println("Successfully reloaded ${customFile.getFileName()}.")
        }
    }

    fun getAllCratesNames(): ArrayList<String> {
        val files = ArrayList<String>()

        val crateDir = File(dataFolder, "/crates")

        crateDir.list()?.forEach {
            if (it.endsWith(".yml")) {
                files.add(it.replace(".yml".toRegex(), ""))
            }
        }

        return files
    }

    class CustomFile(private val fileName: String, private val homeFolder: String) {

        init {
            create()
        }

        private var file: FileConfiguration? = null

        private val blankFile: File? = null

        var getFile = file

        fun getFileName() = fileName

        fun getHomeFolder() = homeFolder

        fun getFile() = file

        fun exists() = file != null

        private fun create() {

            val newFile = File(dataFolder, "/$homeFolder/$fileName")

            runCatching {
                file = if (newFile.exists()) {
                    YamlConfiguration.loadConfiguration(newFile)
                } else {
                    null
                }
            }.onFailure {
                newFile.mkdir()

                println("Could not save ${newFile.name}!")

                println("The folder $homeFolder/ was not found so it was made.")

                file = null
            }
        }

        fun save() {
            if (file == null) return

            runCatching {
                if (blankFile != null) file?.save(blankFile)
            }.onFailure {
                println("Could not save $fileName")

                it.printStackTrace()
            }.onSuccess {
                println("$fileName has been saved.")
            }
        }

        fun reload() {
            if (file == null) return

            runCatching {
                file = blankFile?.let { YamlConfiguration.loadConfiguration(it) }
            }.onFailure {
                println("Could not reload $fileName")

                it.printStackTrace()
            }.onSuccess {
                println("$fileName has been reloaded.")
            }
        }
    }
}