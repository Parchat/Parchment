package net.parchat.parchment.api

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import net.parchat.parchment.api.events.EventsManager
import net.parchat.parchment.misc.Logger
import java.nio.file.Path
import kotlin.io.path.exists

open class Parcore(private val token: String, private val dataFolder: Path, private val gatewayIntents: Collection<GatewayIntent>, private val logger: org.slf4j.Logger) : ListenerAdapter() {

    private var instance: JDA? = null

    private val eventsManager: EventsManager? = null

    init {
        createDataFolder()

        instance = createJDA()
    }

    private fun createJDA(): JDA? {
        runCatching {
            return JDABuilder.createDefault(token, gatewayIntents).addEventListeners(ParcoreListener()).build()
        }.onFailure {
            Logger.error("Failed to start the Discord Bot Instance!")
            Logger.error(it.cause?.localizedMessage)

            return@onFailure
        }

        return null
    }

    open fun onReady() {}

    open fun onGuildReady(guild: Guild) {}

    open fun onShutdown() {}

    protected fun getJDAInstance() = instance

    protected fun getInstance() = this

    protected fun getEventsManager() = eventsManager

    protected fun getLogger() = logger

    fun getDataFolder() = dataFolder

    private fun createDataFolder() {
        if (dataFolder.exists()) return

        dataFolder.toFile().mkdir()
    }
}