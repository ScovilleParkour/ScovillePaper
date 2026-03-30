package dev.meluhdy.scoville.core.parkourer

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.scoville.achievement.Achievement
import dev.meluhdy.scoville.achievement.AchievementManager
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import org.bukkit.entity.Player
import java.util.UUID

class Parkourer(uuid: UUID): MelodiaItem(uuid) {

    constructor(player: Player) : this(player.uniqueId)

    var currentlyPlaying: UUID? = null
    private val achievements: MutableList<String> = mutableListOf()
    private val courseCompletionCount: HashMap<UUID, Int> = hashMapOf()

    fun hasAchievement(achievement: Achievement<*>) = hasAchievement(achievement.achievementId)
    fun hasAchievement(achievementID: String) = achievements.contains(achievementID)

    fun grantAchievement(achievement: Achievement<*>) {
        Achiev
        achievements.add(achievement.achievementId)
    }
    fun grantAchievement(achievement: String) {
        val ach = AchievementManager.get(achievement) ?: throw IllegalArgumentException("Achievement $achievement not found")
        grantAchievement(ach)
    }

    fun getAchievementList(): List<Achievement<*>> = achievements.mapNotNull { achievement -> AchievementManager.get(achievement) }

    fun getCourseCompletions(course: UUID): Int {
        return courseCompletionCount[course] ?: 0
    }
    fun getCourseCompletions(course: AbstractCourse): Int {
        return this.getCourseCompletions(course.uuid)
    }
    fun hasCompletedCourse(course: UUID): Boolean {
        return this.getCourseCompletions(course) > 0
    }
    fun hasCompletedCourse(course: AbstractCourse): Boolean {
        return this.hasCompletedCourse(course.uuid)
    }

    fun incrementCourseCompletions(course: UUID): Int {
        courseCompletionCount[course] = (courseCompletionCount[course] ?: 0) + 1
        return courseCompletionCount[course]!!
    }
    fun incrementCourseCompletions(course: AbstractCourse): Int {
        return this.incrementCourseCompletions(course.uuid)
    }

    fun getAllCourseCompletions(): HashMap<AbstractCourse, Int> {
        return HashMap(courseCompletionCount
            .filter { entry -> CourseManager.exists(entry.key) }
            .mapKeys { entry -> CourseManager.get(entry.key)!! })
    }

}