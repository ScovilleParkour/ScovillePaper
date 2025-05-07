package dev.meluhdy.scoville.core.parkourer

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.scoville.achievement.Achievement
import dev.meluhdy.scoville.achievement.AchievementManager
import org.bukkit.entity.Player
import java.util.UUID

class Parkourer(uuid: UUID): MelodiaItem(uuid) {

    constructor(player: Player) : this(player.uniqueId)

    var currentlyPlaying: UUID? = null
    private val achievements: MutableList<String> = mutableListOf()

    fun hasAchievement(achievement: Achievement<*>) = hasAchievement(achievement.achievementId)
    fun hasAchievement(achievementID: String) = achievements.contains(achievementID)

    fun grantAchievement(achievement: Achievement<*>) = achievements.add(achievement.achievementId)
    fun grantAchievement(achievement: String) {
        val ach = AchievementManager.get(achievement) ?: throw IllegalArgumentException("Achievement $achievement not found")
        grantAchievement(ach)
    }

    fun getAchievementList(): List<Achievement<*>> = achievements.mapNotNull { achievement -> AchievementManager.get(achievement) }

}