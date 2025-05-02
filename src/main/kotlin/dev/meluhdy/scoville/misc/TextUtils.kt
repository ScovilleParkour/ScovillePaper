package dev.meluhdy.scoville.misc

import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.Scoville
import org.bukkit.Bukkit

fun TextUtils.broadcastChat(stringId: String, vararg args: Any) {
    Bukkit.getOnlinePlayers().forEach { player ->
        player.sendMessage { legacyToMiniMessage(translate(Scoville.plugin, stringId, player.locale(), *args)).fromMiniMessage() }
    }
}