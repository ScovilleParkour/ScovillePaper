package dev.meluhdy.scoville.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.command.pk.MenuCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component

class PKCommand: MelodiaCommand("pk") {

    override val children: ArrayList<MelodiaCommand> = arrayListOf(
        MenuCommand()
    )

    @Suppress("Unstable")
    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(Component.text("No"))
        return Command.SINGLE_SUCCESS
    }

}