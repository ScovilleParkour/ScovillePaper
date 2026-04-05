package dev.meluhdy.scoville.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.command.pk.MenuCommand
import dev.meluhdy.scoville.command.pk.PlateCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component

@Suppress("UnstableApiUsage")
object PKCommand: MelodiaCommand("pk") {

    override val children: List<MelodiaCommand> = listOf(
        MenuCommand,
        PlateCommand
    )
    override val arguments: List<RequiredArgumentBuilder<CommandSourceStack, *>> = listOf()

    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(Component.text("No"))
        return Command.SINGLE_SUCCESS
    }

}