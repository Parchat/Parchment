package net.parchat.parchment.misc.embeds

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.parchat.parchment.misc.Methods.toColor

object Embed {

    private val embed = EmbedBuilder()
    private val fields = Fields(embed)

    init {
        embed.setColor(EmbedColors.DEFAULT.code.toColor())
    }

    fun title(title: String) {
        embed.setTitle(title)
    }

    fun footer(footer: String, icon: String? = null) {
        embed.setFooter(footer, icon)
    }

    fun footer(user: User) {
        embed.setFooter("Requested by: ${user.asTag}", user.avatarUrl)
    }

    fun fields(block: Fields.() -> Unit) {
        block(fields)
    }

    fun color(hex: String) {
        embed.setColor(hex.toColor())
    }

    fun color(color: EmbedColors) {
        embed.setColor(color.code.toColor())
    }

    fun thumbnail(url: String?) {
        embed.setThumbnail(url)
    }

    fun thumbnail(user: User) {
        embed.setThumbnail(user.avatarUrl)
    }

    fun image(url: String) {
        embed.setImage(url)
    }

    fun author(author: String, image: String? = null) {
        embed.setAuthor(author, null, image)
    }

    fun author(author: String, user: User) {
        embed.setAuthor(author, null, user.avatarUrl)
    }

    fun author(author: User) {
        embed.setAuthor(author.asTag, null, author.avatarUrl)
    }

    fun description(description: String) {
        embed.setDescription(description)
    }

    fun build(): MessageEmbed {
        return embed.build()
    }
}

class Fields(private val embed: EmbedBuilder) {

    fun field(title: String, body: String,  inline: Boolean = false) {
        embed.addField(title, body, inline)
    }

    fun field(field: MessageEmbed.Field) {
        embed.addField(field)
    }

    fun empty(inline: Boolean = false) {
        embed.addBlankField(inline)
    }
}

inline fun embed(builder: Embed.() -> Unit): MessageEmbed {
    return Embed.apply(builder).build()
}

enum class EmbedColors(val code: String) {
    DEFAULT("#1C3FB8"),
    SUCCESS("#72D689"),
    FAIL("#D67272"),
    WARNING("#F69E7B"),
    EDIT("#72D1D6")
}