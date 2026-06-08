package dev.meluhdy.scoville.event.listener

import dev.meluhdy.melodia.utils.FileUtils.requireString
import dev.meluhdy.melodia.utils.FileUtils.requireStringList
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.melodia.utils.next
import dev.meluhdy.melodia.utils.sendMessage
import dev.meluhdy.melodia.utils.toLegacyMessage
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.core.parkourer.Parkourer
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.event.event.CourseCompleteEvent
import dev.meluhdy.scoville.event.event.CourseJoinEvent
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import dev.meluhdy.scoville.misc.ScovilleConstants
import dev.meluhdy.scoville.misc.ScovilleConstants.getScovilleCourse
import dev.meluhdy.scoville.misc.ScovilleConstants.getScovilleCourseId
import dev.meluhdy.scoville.misc.track.RankTrack
import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import java.util.UUID

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
    }

    @EventHandler
    fun onCourseComplete(e: CourseCompleteEvent) {
        val player = e.player
        val parkourer = ParkourerManager.getOrCreate(player) { Parkourer(player) }
        parkourer.incrementCourseCompletions(e.course)
    }

    @EventHandler
    fun onPlayerRankup(e: CourseCompleteEvent) {
        val player = e.player
        val course = e.course
        if (course !is RankupCourse) return
        val parkourer = ParkourerManager.getOrCreate(player) { Parkourer(player) }
        if (parkourer.rank != course.rank) return
        val next = course.rank.next()
        if (next == RankupCourse.Rank.UNKNOWN) return
        parkourer.rank = next
    }

    @EventHandler
    fun onCheckpointSign(e: PlayerInteractEvent) {
        if (e.action != Action.RIGHT_CLICK_BLOCK) return

        val player = e.player
        if (!player.isOnGround) {
            player.sendMessage(Scoville.plugin, "chat.checkpoint.not_on_ground")
            return
        }
        val block = e.clickedBlock ?: return
        val state = block.state
        if (state !is Sign) return

        fun fromTag() = block.getScovilleCourse()
        fun fromSign(): AbstractCourse? {
            val signFormat = (Scoville.plugin.config as YamlConfiguration).requireStringList("checkpoint_sign_format")
            if (state.getSide(Side.FRONT).line(0).toLegacyMessage() != signFormat[0] || state.getSide(Side.FRONT).line(2).toLegacyMessage() != signFormat[2]) return null
            return CourseManager.get(PlainTextComponentSerializer.plainText().serialize(state.getSide(Side.FRONT).line(1)))
        }

        val course = fromSign() ?: fromTag() ?: return
        val parkourer = ParkourerManager.getOrCreate(player) { Parkourer(player) }

        parkourer.setCheckpoint(course, player.location)
        player.sendMessage(Scoville.plugin, "chat.checkpoint.set", course.coloredName ?: course.name ?: "UNKNOWN COURSE")
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.3f, 0.5f)

        e.isCancelled = true
    }

}