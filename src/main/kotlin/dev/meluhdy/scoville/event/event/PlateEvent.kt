package dev.meluhdy.scoville.event.event

import dev.meluhdy.scoville.core.plate.Plate
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlateEvent(p: Player, val plate: Plate, val plateType: Plate.PlateType) : PlayerEvent(p, true) {

    companion object {
        @JvmStatic
        val HANDLERS: HandlerList = HandlerList()

        @JvmStatic
        @Suppress("unused")
        fun getHandlerList(): HandlerList = HANDLERS
    }

    override fun getHandlers(): HandlerList = HANDLERS

}