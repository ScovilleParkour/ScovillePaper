package dev.meluhdy.scoville.event.listener

import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.event.event.GrantAchievementEvent
import dev.meluhdy.scoville.misc.broadcastChat
import net.kyori.adventure.text.Component.text
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object AchievementBroadcastListener: Listener {

    @EventHandler
    fun on(e: GrantAchievementEvent) {
        TextUtils.broadcastChat("achievement.broadcast", text(e.ach.diff.color), text(e.player.name), TranslatedString(e.ach.nameId, arrayOf()))
    }

}