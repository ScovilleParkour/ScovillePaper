package dev.meluhdy.scoville.command.pk

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.gui.MelodiaGUI
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

object MenuCommand: MelodiaCommand("menu") {

    lateinit var mainMenu: (Player) -> MelodiaGUI

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf()

    @UserOnly
    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        this.mainMenu(context.source.sender as Player).open()
        return Command.SINGLE_SUCCESS
    }

}