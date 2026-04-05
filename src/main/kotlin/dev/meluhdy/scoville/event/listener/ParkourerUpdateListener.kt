package dev.meluhdy.scoville.event.listener

import dev.meluhdy.scoville.core.parkourer.Parkourer
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.core.plate.Plate
import dev.meluhdy.scoville.core.plate.PlateManager
import dev.meluhdy.scoville.event.event.CourseCompleteEvent
import dev.meluhdy.scoville.event.event.CourseJoinEvent
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import dev.meluhdy.scoville.event.event.PlateEvent
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.material.PressureSensor

object ParkourerUpdateListener: Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        ParkourerManager.getOrCreate(player) { Parkourer(player) }
    }

    @EventHandler
    fun onCourseJoin(e: CourseJoinEvent) {
        val player = e.player
        val course = e.course
        val parkourer = ParkourerManager.getOrCreate(player) { Parkourer(player) }
        parkourer.currentlyPlaying = course.uuid
    }

    @EventHandler
    fun onCourseLeave(e: CourseLeaveEvent) {
        val player = e.player
        val parkourer = ParkourerManager.getOrCreate(player) { Parkourer(player) }
        parkourer.currentlyPlaying = null
        // TODO: Fix Parkourer Hotbar
    }

    @EventHandler
    fun onPlate(e: PlayerInteractEvent) {
        val block = e.clickedBlock ?: return
        if (!Tag.PRESSURE_PLATES.isTagged(block.type)) return

        val plate = PlateManager.get { it.location == block.location } ?: return

        val blockBelow = block.getRelative(BlockFace.DOWN)
        when (blockBelow.type) {
            Material.RED_CONCRETE -> {
                PlateEvent(e.player, plate, Plate.PlateType.BEGIN).callEvent()
            }
            Material.GREEN_CONCRETE -> {
                PlateEvent(e.player, plate, Plate.PlateType.END).callEvent()
            }
            else -> return
        }
    }

    @EventHandler
    fun onEndPlate(e: PlateEvent) {
        if (e.plateType != Plate.PlateType.END) return

        val course = e.plate.getCourse() ?: return
        // TODO: Handle leaderboard time
        CourseCompleteEvent(e.player, course).callEvent()
        CourseLeaveEvent(e.player, course).callEvent()
    }

}