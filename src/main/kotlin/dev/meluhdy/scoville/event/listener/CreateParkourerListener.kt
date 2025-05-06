package dev.meluhdy.scoville.event.listener

import dev.meluhdy.scoville.core.parkourer.Parkourer
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object CreateParkourerListener: Listener {

    @EventHandler
    fun on(e: PlayerJoinEvent) {
        val player = e.player
        ParkourerManager.getOrCreate(player) { Parkourer(player) }
    }

}