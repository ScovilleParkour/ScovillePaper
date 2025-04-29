package dev.meluhdy.scoville.commands.TestCommand

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaCommand
import io.papermc.paper.command.brigadier.CommandSourceStack

class Test2Command : MelodiaCommand("2") {

    override val children: ArrayList<MelodiaCommand>
        get() = arrayListOf()

    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendPlainMessage("2")
        return Command.SINGLE_SUCCESS
    }


}