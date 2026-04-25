package dev.meluhdy.scoville.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.event.event.CourseLeaveEvent
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object LobbyCommand : MelodiaCommand("l") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf()

    @UserOnly
    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        val player = context.source.sender as Player

        val parkourer = ParkourerManager.get(player)
        if (parkourer?.currentlyPlaying != null) {
            val course = parkourer.currentlyPlaying?.let { CourseManager.get(it) }
            if (course != null) {
                CourseLeaveEvent(player, course).callEvent()
            }
        }

        // TODO: Don't be hardcoded!!!
        player.teleport(Location(Bukkit.getWorld("courses_released"), 0.5, 50.0, 0.5, -90.0f, 0.0f))

        return Command.SINGLE_SUCCESS
    }

}