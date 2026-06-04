package dev.meluhdy.scoville.core.parkourer

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.misc.track.RankTrack
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import java.util.UUID

class Parkourer(uuid: UUID): MelodiaItem(uuid) {

    constructor(player: Player) : this(player.uniqueId)

    var currentlyPlaying: UUID? = null
    internal val courseCompletionCount: HashMap<UUID, Int> = hashMapOf()
    var rank: RankupCourse.Rank
        get() = RankTrack.fromPlayer(this.uuid)
        set(r) = RankTrack.setGroup(this.uuid, r)
    val checkpoints: HashMap<UUID, Location> = hashMapOf()

    fun getPlayer(): Player? = Bukkit.getPlayer(this.uuid)
    fun getOfflinePlayer(): OfflinePlayer = Bukkit.getOfflinePlayer(this.uuid)

    fun getCourseCompletions(course: UUID): Int = courseCompletionCount[course] ?: 0
    fun getCourseCompletions(course: AbstractCourse): Int = this.getCourseCompletions(course.uuid)
    fun hasCompletedCourse(course: UUID): Boolean = this.getCourseCompletions(course) > 0
    fun hasCompletedCourse(course: AbstractCourse): Boolean = this.hasCompletedCourse(course.uuid)

    fun incrementCourseCompletions(course: UUID): Int {
        if (!CourseManager.exists(course)) {
            courseCompletionCount.remove(course)
            return 0
        }
        courseCompletionCount[course] = (courseCompletionCount[course] ?: 0) + 1
        return courseCompletionCount[course]!!
    }
    fun incrementCourseCompletions(course: AbstractCourse): Int = this.incrementCourseCompletions(course.uuid)

    fun getAllCourseCompletions(): HashMap<AbstractCourse, Int> {
        synchronized(CourseManager) {
            return HashMap(courseCompletionCount
                .mapNotNull { (uuid, count) -> CourseManager.get(uuid)?.let { it to count } }
                .toMap())
        }
    }

    fun getPlayingCourse(): AbstractCourse? = currentlyPlaying?.let { CourseManager.get(it) }
    fun setPlayingCourse(course: AbstractCourse) {
        currentlyPlaying = course.uuid
    }

    fun setCheckpoint(course: AbstractCourse, location: Location) {
        this.checkpoints[course.uuid] = location
    }
    fun getCheckpoint(course: AbstractCourse): Location? = this.getCheckpoint(course.uuid)
    fun getCheckpoint(uuid: UUID): Location? = this.checkpoints[uuid]

}