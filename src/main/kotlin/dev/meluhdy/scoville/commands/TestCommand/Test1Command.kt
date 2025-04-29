package dev.meluhdy.scoville.commands.TestCommand

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title

class Test1Command : MelodiaCommand("1") {

    override fun getChildren(): ArrayList<MelodiaCommand> = arrayListOf()

    @UserOnly
    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.showTitle(Title.title(Component.text("Test1"), Component.text("")))
        return Command.SINGLE_SUCCESS
    }

}