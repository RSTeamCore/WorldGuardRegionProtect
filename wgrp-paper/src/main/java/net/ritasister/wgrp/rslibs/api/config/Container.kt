package net.ritasister.wgrp.rslibs.api.config

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.ConfigurationSection
import java.util.*

class Container(section: ConfigurationSection) {

    private val DEFAULT_NULLABLE_MESSAGE = ComponentWrapper("", this)

    var prefix: String? = null
    var miniMessage: MiniMessage = MiniMessage.miniMessage()

    private val messages: MutableMap<String, ComponentWrapper> = HashMap<String, ComponentWrapper>()

    private val nullable: ComponentWrapper? = null

    init {
        prefix = try {
            Objects.requireNonNull(section.getString("prefix"))
        } catch (e: NullPointerException) {
            null
        }
        val data = fromConfigurationToMap(section)
        messages.putAll(data)
    }

    private fun fromConfigurationToMap(section: ConfigurationSection): Map<String, ComponentWrapper> {
        val data: MutableMap<String, ComponentWrapper> = HashMap<String, ComponentWrapper>()
        section.getKeys(false).forEach { key ->
            if (section.isConfigurationSection(key)) {
                section.getConfigurationSection(key)?.let { fromConfigurationToMap(it) }
                    ?.forEach { (keyMessage: String, message: ComponentWrapper) ->
                        if (prefix != null) message.prefix = prefix!!
                        data.put("$key.$keyMessage", message)
                    }
            } else {
                if (section.isString(key)) {
                    data[key] = section.getString(key)?.let { ComponentWrapper(it, this) }!!
                } else if (section.isList(key)) {
                    val list = mutableListOf<String>()
                    for (s in section.getStringList(key)) {
                        list.add(s)
                    }
                    data[key] = ComponentWrapper(list, this)
                }
            }
        }
        return data
    }

    fun has(key: String): Boolean {
        return messages.containsKey(key)
    }

    operator fun get(key: String): ComponentWrapper {
        val message = messages[key] ?: return nullable ?: DEFAULT_NULLABLE_MESSAGE
        if (prefix != null) message.prefix = prefix!!
        return message
    }

    operator fun get(key: String, withPrefix: Boolean): ComponentWrapper {
        val message = messages[key] ?: return nullable ?: DEFAULT_NULLABLE_MESSAGE
        if (prefix != null && withPrefix) message.prefix = prefix!!
        return message
    }


    fun put(key: String, message: ComponentWrapper) {
        messages[key] = message
    }

    fun putIfAbsent(key: String, message: ComponentWrapper) {
        messages.putIfAbsent(key, message)
    }

    fun hasPrefix(): Boolean {
        return prefix != null
    }
}
