package dev.meluhdy.scoville.event.event

import dev.meluhdy.scoville.core.course.AbstractCourse
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class CourseCompleteEvent(p: Player, val course: AbstractCourse, val time: Long? = null) : PlayerEvent(p, true) {

    companion object {
        @JvmStatic
        val HANDLERS: HandlerList = HandlerList()

        @JvmStatic
        @Suppress("unused")
        fun getHandlerList(): HandlerList = HANDLERS
    }

    override fun getHandlers(): HandlerList = HANDLERS

}