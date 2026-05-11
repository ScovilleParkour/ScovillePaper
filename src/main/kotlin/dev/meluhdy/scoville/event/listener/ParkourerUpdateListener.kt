package dev.meluhdy.scoville.event.listener

import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.melodia.utils.toLegacyMessage
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.parkourer.Parkourer
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.core.plate.Plate
import dev.meluhdy.scoville.core.plate.PlateManager
import dev.meluhdy.scoville.event.event.CourseCompleteEvent
import dev.meluhdy.scoville.event.event.CourseJoinEvent
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import dev.meluhdy.scoville.event.event.PlateEvent
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.Tag
import org.bukkit.block.BlockFace
import org.bukkit.block.Sign
import org.bukkit.block.data.Lightable
import org.bukkit.block.sign.Side
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

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
    fun onCourseComplete(e: CourseCompleteEvent) {
        val player = e.player
        val parkourer = ParkourerManager.getOrCreate(player) { Parkourer(player) }
        parkourer.incrementCourseCompletions(e.course)
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

    @EventHandler
    fun onCheckpointSign(e: PlayerInteractEvent) {

        if (e.action != Action.RIGHT_CLICK_BLOCK) return

        val state = e.clickedBlock?.state ?: return
        if (state !is Sign) return

        if (
            state.getSide(Side.FRONT).line(0).toLegacyMessage() != "&8[&4Scoville&8]" ||
            state.getSide(Side.FRONT).line(2).toLegacyMessage() != "&2✔ &aCheckpoint &2✔"
        ) return

        val player = e.player
        if (!player.isOnGround) {
            player.sendMessage(TextUtils.translate(Scoville.plugin, "chat.checkpoint.not_on_ground", player.locale()))
            return
        }

        // TODO: Add custom /pk cp support

        val course = CourseManager.get(PlainTextComponentSerializer.plainText().serialize(state.getSide(Side.FRONT).line(1))) ?: return
        // TODO: Add permission check here

        val parkourer = ParkourerManager.get(player) ?: return
        parkourer.setCheckpoint(course, player.location)
        player.sendMessage(TextUtils.translate(Scoville.plugin, "chat.checkpoint.set", player.locale(), course.coloredName ?: course.name ?: "UNKNOWN COURSE").fromLegacyMessage())
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.3f, 0.5f)

        e.isCancelled = true

    }

    fun handleLamp(p: Player, loc: Location, checkOnGround: Boolean = true) {
        if (checkOnGround && !p.isOnGround) return
        val parkourer = ParkourerManager.get(p) ?: return
        val course = parkourer.getPlayingCourse() ?: return

        val block = loc.block.getRelative(BlockFace.DOWN)
        if (block.type != Material.REDSTONE_LAMP) return
        val data = block.blockData as Lightable
        if (data.isLit) return

        parkourer.gotoCheckpoint(course)
        p.playSound(p.location, Sound.ENTITY_ENDERMAN_TELEPORT, 100.0F, 100.0F)
    }

    @EventHandler
    fun onRedstoneLamp(e: PlayerMoveEvent) = handleLamp(e.player, e.to)

    @EventHandler
    fun joinOnLamp(e: PlayerJoinEvent) = handleLamp(e.player, e.player.location, false)

}