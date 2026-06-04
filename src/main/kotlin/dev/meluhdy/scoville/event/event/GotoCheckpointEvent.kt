package dev.meluhdy.scoville.event.event

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class GotoCheckpointEvent(p: Player, val checkpoint: Location) : PlayerEvent(p) {

    companion object {
        @JvmStatic
        val HANDLERS: HandlerList = HandlerList()

        @JvmStatic
        @Suppress("unused")
        fun getHandlerList(): HandlerList = HANDLERS
    }

    override fun getHandlers(): HandlerList = HANDLERS

}