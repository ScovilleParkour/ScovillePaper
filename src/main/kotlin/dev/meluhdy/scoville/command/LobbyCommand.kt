package dev.meluhdy.scoville.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

@Suppress("UnstableApiUsage")
object LobbyCommand : MelodiaCommand("l") {

    override val children: ArrayList<MelodiaCommand> = arrayListOf()

    @UserOnly
    override fun onCommand(context: CommandContext<CommandSourceStack>): Int {
        val player = context.source.sender as Player
        // TODO: Don't be hardcoded!!!
        player.teleport(Location(Bukkit.getWorld("courses_released"), 100000.5, 4.0, 100000.5, -90.0f, 0.0f))
        return Command.SINGLE_SUCCESS
    }

}