package dev.meluhdy.scoville.misc.track

import dev.meluhdy.scoville.core.course.courses.RankupCourse.Rank
import dev.meluhdy.scoville.misc.Tracked

object RankTrack : Tracked<Rank>() {

    override val prefix: String = "group.rank"
    override val trackName: String = "track.rank"
    override val default: Rank = Rank.BELL

    override fun getEntries(): List<Rank> = Rank.entries

    override fun toGroupRoot(obj: Rank): String = obj.name.lowercase()

    override fun fromGroupRoot(str: String): Rank? = getEntries().find { it.name.equals(str, ignoreCase = true) }

}