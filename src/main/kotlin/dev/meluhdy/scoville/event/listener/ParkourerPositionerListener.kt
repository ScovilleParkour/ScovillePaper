package dev.meluhdy.scoville.event.listener

import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.event.event.CourseJoinEvent
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import dev.meluhdy.scoville.event.event.GotoCheckpointEvent
import dev.meluhdy.scoville.misc.ScovilleConstants.getScovilleLocation
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.Sign
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object ParkourerPositionerListener : Listener {

    @EventHandler
    fun onPlayerJoinCourse(e: CourseJoinEvent) {
        val player = e.player
        val course = e.course
        Bukkit.getScheduler().scheduleSyncDelayedTask(Scoville.plugin) {
            course.startLocation?.let { player.teleport(it) }
        }
    }

    @EventHandler
    fun onPlayerLeaveCourse(e: CourseLeaveEvent) {
        val player = e.player
        Bukkit.getScheduler().scheduleSyncDelayedTask(Scoville.plugin) {
            player.performCommand("l")
        }
    }

    @EventHandler
    fun onCheckpoint(e: GotoCheckpointEvent) = e.player.teleport(e.checkpoint)

    @EventHandler
    fun onTeleportPlate(e: PlayerInteractEvent) {
        val block = e.clickedBlock ?: return
        if (e.action != Action.PHYSICAL || block.type != Material.HEAVY_WEIGHTED_PRESSURE_PLATE) return
        val blockBelow = block.getRelative(BlockFace.DOWN)
        if (blockBelow.type != Material.YELLOW_CONCRETE) return
        this.zhwoopPlayer(e.player, block)
    }

    @EventHandler
    fun onTeleportSign(e: PlayerInteractEvent) {
        val block = e.clickedBlock ?: return
        if (e.action != Action.RIGHT_CLICK_BLOCK || block.state !is Sign) return
        this.zhwoopPlayer(e.player, block)
        e.isCancelled = true
    }

    private fun zhwoopPlayer(player: Player, block: Block) {
        val location: Location = block.getScovilleLocation() ?: return
        player.teleport(location)
        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 100.0F, 100.0F)
    }

}