package dev.meluhdy.scoville.command.pk

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.gui.MainMenuGUI
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

@Suppress("UnstableApiUsage")
class MenuCommand: MelodiaCommand("menu") {

    override val children: ArrayList<MelodiaCommand> = arrayListOf()

    @UserOnly
    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        MainMenuGUI(context.source.sender as Player).open()
        return Command.SINGLE_SUCCESS
    }

}