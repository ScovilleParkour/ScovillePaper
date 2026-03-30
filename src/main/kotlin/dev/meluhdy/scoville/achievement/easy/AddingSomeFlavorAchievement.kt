package dev.meluhdy.scoville.achievement.easy

import dev.meluhdy.scoville.achievement.Achievement
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.event.event.CourseCompleteEvent
import dev.meluhdy.scoville.misc.PermissionUtils
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.inventory.ItemStack

object AddingSomeFlavorAchievement: Achievement<CourseCompleteEvent>(EventPriority.LOWEST) {

    override val achievementId: String = "addingSomeFlavor"
    override val nameId: String = "achievement.test.name"
    override val descId: String = "achievement.test.desc"
    override val diff: AchievementDifficulty = AchievementDifficulty.EASY
    override val baseStack: ItemStack = ItemStack(Material.STONE)

    override fun check(event: CourseCompleteEvent): Boolean {
        val player = event.player
        val parkourer = ParkourerManager.get(player) ?: return false
        val rank = PermissionUtils.getRank(parkourer)

        return rank > RankupCourse.Rank.GUAJILLO
    }

    override fun onAchievementGet(p: Player) {
        p.sendMessage("You stupid bitch")
    }

}