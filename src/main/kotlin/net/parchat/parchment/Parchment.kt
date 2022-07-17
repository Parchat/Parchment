package net.parchat.parchment

import dev.triumphteam.core.jda.JdaApplication
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.requests.GatewayIntent
import net.parchat.parchment.misc.fetchToken

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

class Parchment(token: String) : JdaApplication(token, INTENTS) {

    private val parch = this

    override fun onStart() {

    }

    override fun onReady() {

    }

    override fun onGuildReady(guild: Guild) {

    }

    override fun onStop() {

    }
}

fun main(args: Array<String>) {
    Parchment(fetchToken(args).toString())
}