package net.parchat.parchment.misc

import net.parchat.parchment.Parchment
import org.slf4j.LoggerFactory

object Logger {

    private val logger = LoggerFactory.getLogger(Parchment::javaClass.name)

    fun info(msg: String) = logger.info(msg)

    fun error(msg: String?) = logger.error(msg)

    fun warning(msg: String) = logger.warn(msg)

    fun trace(msg: String) = logger.trace(msg)

}