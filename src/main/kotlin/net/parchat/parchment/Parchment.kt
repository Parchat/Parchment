package net.parchat.parchment

import dev.triumphteam.core.jda.JdaApplication
import me.mattstudios.config.SettingsManager
import me.mattstudios.config.SettingsManagerBuilder
import me.mattstudios.config.annotations.TargetObject
import me.mattstudios.config.beanmapper.PropertyMapper
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.requests.GatewayIntent
import net.parchat.parchment.config.Config
import net.parchat.parchment.config.Settings
import net.parchat.parchment.misc.fetchToken
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

class Parchment(token: String) : JdaApplication(token, INTENTS) {

    private val parchment = this

    override fun onStart() {

    }

    override fun onReady() {

    }

    override fun onGuildReady(guild: Guild) {

    }
}

fun main(args: Array<String>) {

    val config = SettingsManager
        .from(File("config.yml"))
        .configurationData(Settings::class.java)
        .create()

    Parchment(fetchToken(args).toString())
}