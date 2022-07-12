package net.parchat.parchment

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.requests.GatewayIntent
import net.parchat.parchment.misc.Methods
import net.parchat.parcore.Parcore
import net.parchat.parcore.listeners.interfaces.EventHandler
import java.io.File

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

class Parchment(token: String) : Parcore(token, File("./parchment"), INTENTS) {

    @EventHandler
    override fun onReady(event: ReadyEvent) {
        logger.info("We've booted.")

        logger.info(jda.guilds.size.toString())
    }

    @EventHandler
    override fun onEnable() {

    }

    @EventHandler
    override fun onGuildEnable(guild: Guild) {

    }
}

fun main(args: Array<String>) {
    Parchment(Methods.fetchToken(args).toString())
}