package dev.meluhdy.scoville.achievement.easy

import dev.meluhdy.scoville.achievement.Achievement
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object TestAchievement: Achievement<AsyncChatEvent>() {

    override val achievementId: String = "testAchievement"
    override val nameId: String = "achievement.test.name"
    override val descId: String = "achievement.test.desc"
    override val diff: AchievementDifficulty = AchievementDifficulty.EASY
    override val baseStack: ItemStack = ItemStack(Material.STONE)

    override fun check(event: AsyncChatEvent): Boolean {
        val ser = PlainTextComponentSerializer.plainText()

        return ser.serialize(event.originalMessage()) == "hi"
    }

    override fun onAchievementGet(p: Player) {
        p.sendMessage("You stupid bitch")
    }


}