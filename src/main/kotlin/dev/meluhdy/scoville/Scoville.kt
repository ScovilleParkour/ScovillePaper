package dev.meluhdy.scoville

import dev.meluhdy.melodia.MelodiaPlugin
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.commands.TestCommand.TestCommand

class Scoville : MelodiaPlugin() {

    override fun getCommands(): ArrayList<MelodiaCommand> = arrayListOf(
        TestCommand()
    )

}
