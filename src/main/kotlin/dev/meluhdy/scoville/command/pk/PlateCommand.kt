package dev.meluhdy.scoville.command.pk

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.plate.Plate
import dev.meluhdy.scoville.core.plate.PlateManager
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.entity.Player
import java.util.UUID

object PlateCommand : MelodiaCommand("plate") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<RequiredArgumentBuilder<CommandSourceStack, *>> = listOf(
        Commands.argument("course", StringArgumentType.string())
    )

    @UserOnly
    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        val plateBlock = (context.source.sender as Player).getTargetBlock(null, 5)
        if (!Tag.PRESSURE_PLATES.isTagged(plateBlock.type)) {
            context.source.sender.sendMessage("You need to be looking at a plate!")
            return Command.SINGLE_SUCCESS
        }
        val courseName: String = try {
            context.getArgument("course", String::class.java)
        } catch (_: IllegalArgumentException) {
            PlateManager.delete { plate -> plate.location == plateBlock.location }
            return Command.SINGLE_SUCCESS
        }
        val course = CourseManager.get(courseName)
        if (course == null) {
            context.source.sender.sendMessage("Invalid course: $courseName")
            return Command.SINGLE_SUCCESS
        }
        val plate = Plate(UUID.randomUUID())
        plate.location = plateBlock.location
        plate.setCourse(course)
        PlateManager.add(plate)
        return Command.SINGLE_SUCCESS
    }

}