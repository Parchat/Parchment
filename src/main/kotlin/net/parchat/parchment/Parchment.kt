package net.parchat.parchment

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.DisconnectEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.hooks.SubscribeEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import net.parchat.parchment.misc.Methods

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

fun main(args: Array<String>) {
    JDABuilder.createDefault(Methods.fetchToken(args), INTENTS)
        .addEventListeners(
            ParchmentBase()
        ).build().awaitReady()
}

class ParchmentBase : ListenerAdapter() {

    @SubscribeEvent
    override fun onReady(event: ReadyEvent) {}

    @SubscribeEvent
    override fun onGuildReady(event: GuildReadyEvent) {
        println("Guten Tag!")
    }

    @SubscribeEvent
    override fun onDisconnect(event: DisconnectEvent) {
        println("Wiedersehen")
    }
}