package dev.meluhdy.scoville.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.command.pk.MenuCommand
import dev.meluhdy.scoville.command.pk.PlateCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component

object PKCommand: MelodiaCommand("pk") {

    override val children: List<MelodiaCommand> = listOf(
        MenuCommand,
        PlateCommand
    )
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(Component.text("No"))
        return Command.SINGLE_SUCCESS
    }

}