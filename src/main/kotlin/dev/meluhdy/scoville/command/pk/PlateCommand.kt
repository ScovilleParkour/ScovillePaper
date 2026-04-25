package dev.meluhdy.scoville.command.pk

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.plate.Plate
import dev.meluhdy.scoville.core.plate.PlateManager
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.UUID

object PlateCommand : MelodiaCommand("plate") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("course", StringArgumentType.greedyString(), this::addPlate)
    )

    private fun getBlock(player: Player): Block? {
        val block = player.getTargetBlock(null, 5)
        if (!Tag.PRESSURE_PLATES.isTagged(block.type)) {
            player.sendMessage("You need to be looking at a plate!")
            return null
        }
        return block
    }

    @UserOnly
    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        val plateBlock = this.getBlock(context.source.sender as Player) ?: return Command.SINGLE_SUCCESS
        PlateManager.delete { plate -> plate.location == plateBlock.location }
        return Command.SINGLE_SUCCESS
    }

    @UserOnly
    fun addPlate(context: CommandContext<CommandSourceStack>): Int {
        val plateBlock = this.getBlock(context.source.sender as Player) ?: return 0
        val courseName = context.getArgument("course", String::class.java)
        val course = CourseManager.get(courseName)
        if (course == null) {
            context.source.sender.sendMessage("Invalid course: $courseName")
            return 0
        }
        val plate = Plate(UUID.randomUUID())
        plate.location = plateBlock.location
        plate.setCourse(course)
        PlateManager.add(plate)
        return Command.SINGLE_SUCCESS
    }

}