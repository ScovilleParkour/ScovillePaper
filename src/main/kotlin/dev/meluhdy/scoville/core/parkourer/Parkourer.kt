package dev.meluhdy.scoville.core.parkourer

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.misc.PermissionUtils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.UUID

class Parkourer(uuid: UUID): MelodiaItem(uuid) {

    constructor(player: Player) : this(player.uniqueId)

    var currentlyPlaying: UUID? = null
    internal val courseCompletionCount: HashMap<UUID, Int> = hashMapOf()
    var rank: RankupCourse.Rank
        get() = PermissionUtils.getRank(this)
        set(r) = PermissionUtils.setRank(this, r)

    fun getPlayer(): Player? = Bukkit.getPlayer(this.uuid)
    fun getOfflinePlayer(): OfflinePlayer = Bukkit.getOfflinePlayer(this.uuid)

    fun getCourseCompletions(course: UUID): Int = courseCompletionCount[course] ?: 0
    fun getCourseCompletions(course: AbstractCourse): Int = this.getCourseCompletions(course.uuid)
    fun hasCompletedCourse(course: UUID): Boolean = this.getCourseCompletions(course) > 0
    fun hasCompletedCourse(course: AbstractCourse): Boolean = this.hasCompletedCourse(course.uuid)

    fun incrementCourseCompletions(course: UUID): Int {
        courseCompletionCount[course] = (courseCompletionCount[course] ?: 0) + 1
        return courseCompletionCount[course]!!
    }
    fun incrementCourseCompletions(course: AbstractCourse): Int = this.incrementCourseCompletions(course.uuid)

    fun getAllCourseCompletions(): HashMap<AbstractCourse, Int> {
        return HashMap(courseCompletionCount
            .filter { entry -> CourseManager.exists(entry.key) }
            .mapKeys { entry -> CourseManager.get(entry.key)!! })
    }

}