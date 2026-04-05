package dev.meluhdy.scoville.command.pk

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.gui.MainMenuGUI
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

@Suppress("UnstableApiUsage")
object MenuCommand: MelodiaCommand("menu") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<RequiredArgumentBuilder<CommandSourceStack, *>> = listOf()

    @UserOnly
    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        MainMenuGUI(context.source.sender as Player).open()
        return Command.SINGLE_SUCCESS
    }

}