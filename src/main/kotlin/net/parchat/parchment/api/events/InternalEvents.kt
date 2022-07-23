package net.parchat.parchment.api.events

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.parchat.parchment.api.events.interfaces.ListenerAdapter
import net.parchat.parchment.misc.Logger
import java.lang.reflect.Method

class InternalEvents(private val method: Method?, private val listener: ListenerAdapter?, private val event: Class<GenericEvent>?) : EventListener {

    override fun onEvent(e: GenericEvent) {
        if (event != e.javaClass) return

        runCatching {
            method?.invoke(listener, e)
        }.onFailure {
            Logger.error("Failed to invoke ${listener.toString()}")
            Logger.error(it.cause?.localizedMessage)
        }
    }
}