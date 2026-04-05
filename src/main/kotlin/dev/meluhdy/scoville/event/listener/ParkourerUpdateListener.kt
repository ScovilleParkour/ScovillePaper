package dev.meluhdy.scoville.event.listener

import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.parkourer.Parkourer
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.core.plate.Plate
import dev.meluhdy.scoville.core.plate.PlateManager
import dev.meluhdy.scoville.event.event.CourseCompleteEvent
import dev.meluhdy.scoville.event.event.CourseJoinEvent
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import dev.meluhdy.scoville.event.event.PlateEvent
import org.bukkit.Bukkit
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

        Scoville.plugin.logger.debug("Plate hit at ${block.location}")
        PlateManager.getAll().forEach { plate -> Scoville.plugin.logger.debug("Plate: ${plate.location} | ${plate.location == block.location}") }

        val plate = PlateManager.get { it.location == block.location } ?: return

        val blockBelow = block.getRelative(BlockFace.DOWN)
        Scoville.plugin.logger.debug("Bottom Block: ${blockBelow.type}")
        when (blockBelow.type) {
            Material.LIME_CONCRETE -> {
                Bukkit.getAsyncScheduler().runNow(Scoville.plugin) {
                    Scoville.plugin.logger.debug("Begun Course")
                    PlateEvent(e.player, plate, Plate.PlateType.BEGIN).callEvent()
                }
            }
            Material.RED_CONCRETE -> {
                Bukkit.getAsyncScheduler().runNow(Scoville.plugin) {
                    Scoville.plugin.logger.debug("Ended Course")
                    PlateEvent(e.player, plate, Plate.PlateType.END).callEvent()
                }
            }
            else -> return
        }
    }

    @EventHandler
    fun onEndPlate(e: PlateEvent) {
        if (e.plateType != Plate.PlateType.END) return

        val course = e.plate.getCourse() ?: return
        Scoville.plugin.logger.debug("${e.player.name} stepped on the end plate of ${course.name} at ${e.plate.location}")
        // TODO: Handle leaderboard time
        CourseCompleteEvent(e.player, course).callEvent()
        CourseLeaveEvent(e.player, course).callEvent()
    }

}