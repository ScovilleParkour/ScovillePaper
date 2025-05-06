package dev.meluhdy.scoville.core.parkourer

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.scoville.achievement.Achievement
import org.bukkit.entity.Player
import java.util.UUID

class Parkourer(uuid: UUID): MelodiaItem(uuid) {

    constructor(player: Player) : this(player.uniqueId)

    var currentlyPlaying: UUID? = null
    private val achievements: MutableList<UUID> = mutableListOf()

    fun hasAchievement(achievement: Achievement<*>) = hasAchievement(achievement.uuid)
    fun hasAchievement(uuid: UUID) = achievements.contains(uuid)

    fun grantAchievement(achievement: Achievement<*>) = grantAchievement(achievement.uuid)
    fun grantAchievement(uuid: UUID) = achievements.add(uuid)

    fun getAchievementList(): List<UUID> = ArrayList(achievements)

}