package net.ritasister.wgrp.rslibs.api.config

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Material
import org.bukkit.command.CommandSender

class ComponentWrapper(private var value: MutableList<String>, private val container: Container) {

    constructor(string: String, container: Container) : this(mutableListOf(string), container)

    constructor(container: Container) : this(mutableListOf<String>(), container)

    var prefix = if (container.hasPrefix()) container.prefix else ""

    private val miniMessage: MiniMessage = MiniMessage.miniMessage()

    fun toList(): List<String> = value

    override fun toString(): String = value.joinToString("\n")

    fun toComponentList(withPrefix: Boolean = false, vararg resolvers: TagResolver): List<TextComponent> = value.map {
        (if (withPrefix) prefix else "" + it)?.let { it1 ->
            miniMessage.deserialize(it1, TagResolver.resolver(resolvers.asIterable()))
        } as TextComponent
    }

    fun toComponent(withPrefix: Boolean = true, vararg resolvers: TagResolver): TextComponent =
        ((if (withPrefix) prefix else "")?.let {
            miniMessage.deserialize(
                it
            )
        } as TextComponent).toBuilder()
            .also { component ->
                value.map {
                    miniMessage.deserialize(it, TagResolver.resolver(resolvers.asIterable()))
                }.mapIndexed { index, it ->
                    component.append(it)
                    if (value.size != 1 && value.lastIndex != index)
                        component.append(Component.newline())
                }
            }.build()

    fun toComponent(): TextComponent = (prefix?.let {
        miniMessage.deserialize(it)
    } as TextComponent).toBuilder()
        .also { component ->
            value.map {
                miniMessage.deserialize(it)
            }.mapIndexed { index, it ->
                component.append(it)
                if (value.size != 1 && value.lastIndex != index)
                    component.append(Component.newline())
            }
        }.build()

    operator fun plusAssign(string: String) {
        value += string
    }

    operator fun plusAssign(component: TextComponent) {
        value += component.content()
    }


    operator fun minusAssign(string: String) {
        value -= string
    }

    fun send(sender: CommandSender) {
        sender.sendMessage(toComponent())
    }

    fun send(sender: CommandSender, withPrefix: Boolean) {
        sender.sendMessage(toComponent(withPrefix = withPrefix))
    }

    fun replace(from: String, to: String): ComponentWrapper = ComponentWrapper(this.value.map {
        it.replace(from, to)
    }.toMutableList(), container)

    fun replace(from: String, to: Any): ComponentWrapper = replace(from, to.toString())

    fun toMaterial(): Material? = Material.getMaterial(toString().uppercase())

}
