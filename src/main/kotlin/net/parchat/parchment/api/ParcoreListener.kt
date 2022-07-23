package net.parchat.parchment.api

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.ShutdownEvent
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.EventListener

class ParcoreListener : EventListener {

    private val core: Parcore? = null

    fun onReady(event: ReadyEvent) {
        core?.onReady(event)
    }

    fun onGuildReady(event: GuildReadyEvent) {
        core?.onGuildReady(event)
    }

    fun onGuildShutdown(event: ShutdownEvent) {
        core?.onShutdown(event)
    }

    // So it does not yell at us.
    override fun onEvent(event: GenericEvent) {}
}