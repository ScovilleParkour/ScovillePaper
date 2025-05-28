package dev.meluhdy.scoville.event.event

import dev.meluhdy.scoville.achievement.Achievement
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class GrantAchievementEvent(p: Player, val ach: Achievement<*>) : PlayerEvent(p, true) {

    companion object {
        @JvmStatic
        val HANDLERS: HandlerList = HandlerList()

        @JvmStatic
        @Suppress("unused")
        fun getHandlerList(): HandlerList = HANDLERS
    }

    override fun getHandlers(): HandlerList = HANDLERS

}