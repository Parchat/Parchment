package net.parchat.parchment

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.requests.GatewayIntent
import net.parchat.parchment.api.Parcore
import net.parchat.parchment.config.Config
import net.parchat.parchment.misc.getDataFolder
import org.slf4j.LoggerFactory
import java.nio.file.Path

private val INTENTS = listOf(
    GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
    GatewayIntent.GUILD_MESSAGE_REACTIONS,
    GatewayIntent.GUILD_MESSAGE_TYPING,
    GatewayIntent.GUILD_VOICE_STATES,
    GatewayIntent.GUILD_PRESENCES,
    GatewayIntent.GUILD_MESSAGES,
    GatewayIntent.GUILD_MEMBERS,
    GatewayIntent.GUILD_INVITES,
    GatewayIntent.GUILD_BANS
)

class Parchment(token: String) : Parcore(token, Path.of("./parchment"), INTENTS, LoggerFactory.getLogger(Parchment::class.java)) {

    private val parch = this

    override fun onReady() {

    }

    override fun onGuildReady(guild: Guild) {

    }

    override fun onShutdown() {

    }

    fun getParchmentInstance() = parch.getInstance()
}

fun main() {
    Config().reload(getDataFolder())

    Parchment(Config().botToken)
}