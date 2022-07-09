package net.parchat.parchment.misc

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import java.awt.Color

object Methods {

    fun fetchToken(args: Array<String>): String? {
        val option = Options().apply { addOption(Option.builder("t").hasArg().argName("token").required().build()) }
        return DefaultParser().parse(option, args).getOptionValue("t")
    }

    fun String.toColor(): Color {
        return Color(
            Integer.valueOf(substring(1, 3), 16),
            Integer.valueOf(substring(3, 5), 16),
            Integer.valueOf(substring(5, 7), 16)
        )
    }
}