package dev.meluhdy.scoville.commands.TestCommand

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaCommand
import io.papermc.paper.command.brigadier.CommandSourceStack

class TestCommand : MelodiaCommand("test") {

    override val children: ArrayList<MelodiaCommand>
        get() = arrayListOf(
        Test1Command(),
        Test2Command()
    )

    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendPlainMessage("FUCK!!!! TestCommand")
        return Command.SINGLE_SUCCESS
    }

}