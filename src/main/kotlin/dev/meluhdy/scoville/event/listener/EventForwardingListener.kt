package dev.meluhdy.scoville.event.listener

import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.event.event.CourseCompleteEvent
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import dev.meluhdy.scoville.event.event.GotoCheckpointEvent
import dev.meluhdy.scoville.event.event.PlateEvent
import dev.meluhdy.scoville.misc.ScovilleConstants.getScovilleCourse
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Lightable
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import java.util.UUID

object EventForwardingListener : Listener {

    @EventHandler
    fun sendCourseComplete(e: PlayerInteractEvent) {
        if (!this.isPlate(e, Material.RED_CONCRETE)) return
        val block = e.clickedBlock ?: return
        val course = block.getScovilleCourse() ?: return
        Scoville.plugin.logger.debug("Ended Course")
        PlateEvent(e.player, course, PlateEvent.PlateType.END).callEvent()
    }

    @EventHandler
    fun sendCourseStart(e: PlayerInteractEvent) {
        if (!this.isPlate(e, Material.LIME_CONCRETE)) return
        val block = e.clickedBlock ?: return
        val course = block.getScovilleCourse() ?: return
        Scoville.plugin.logger.debug("Started Course")
        PlateEvent(e.player, course, PlateEvent.PlateType.BEGIN).callEvent()
    }

    private fun isPlate(e: PlayerInteractEvent, type: Material): Boolean {
        val block = e.clickedBlock ?: return false
        if (e.action != Action.PHYSICAL || block.type != Material.HEAVY_WEIGHTED_PRESSURE_PLATE) return false
        val blockBelow = block.getRelative(BlockFace.DOWN)
        return blockBelow.type == type
    }

    @EventHandler
    fun handleEndEvent(e: PlateEvent) {
        if (e.plateType != PlateEvent.PlateType.END) return
        val course = e.course
        Scoville.plugin.logger.debug("${e.player.name} stepped on the end plate of ${course.name} at ${e.player.location}")
        CourseCompleteEvent(e.player, course).callEvent()
        CourseLeaveEvent(e.player, course).callEvent()
    }

    @EventHandler
    fun onRedstoneLamp(e: PlayerMoveEvent) = handleLamp(e.player, e.to)

    @EventHandler
    fun joinOnLamp(e: PlayerJoinEvent) = handleLamp(e.player, e.player.location, false)

    fun handleLamp(p: Player, loc: Location, checkOnGround: Boolean = true) {
        if (checkOnGround && !p.isOnGround) return
        val parkourer = ParkourerManager.get(p) ?: return
        val course = parkourer.getPlayingCourse() ?: return

        val block = loc.block.getRelative(BlockFace.DOWN)
        if (block.type != Material.REDSTONE_LAMP) return
        val data = block.blockData as Lightable
        if (data.isLit) return

        GotoCheckpointEvent(p, parkourer.getCheckpoint(course) ?: return).callEvent()
    }

}