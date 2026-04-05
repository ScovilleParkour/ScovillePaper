package dev.meluhdy.scoville.event.listener

import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.event.event.CourseCompleteEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object BroadcastListener : Listener {

    @EventHandler
    fun onCourseComplete(e: CourseCompleteEvent) {
        TextUtils.broadcastChat(Scoville.plugin, "chat.broadcast.course.complete", e.player.name, e.course.coloredName ?: "Unknown Course")
    }

}