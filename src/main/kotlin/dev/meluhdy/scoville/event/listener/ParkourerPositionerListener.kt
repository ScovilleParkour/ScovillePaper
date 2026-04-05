package dev.meluhdy.scoville.event.listener

import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.event.event.CourseJoinEvent
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

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

}