package dev.meluhdy.scoville.misc.track

import dev.meluhdy.scoville.misc.Tracked


object StaffTrack : Tracked<StaffTrack.StaffRank>() {

    override val prefix: String = "group.staff"
    override val trackName: String = "track.staff"
    override val default: StaffRank = StaffRank.DEFAULT

    override fun getEntries(): List<StaffRank> = StaffRank.entries

    override fun toGroupRoot(obj: StaffRank): String = obj.name.lowercase()

    override fun fromGroupRoot(str: String): StaffRank? = getEntries().find { it.name.equals(str, ignoreCase = true) }

    enum class StaffRank {
        DEFAULT,
        JR_BUILDER,
        BUILDER,
        HELPER,
        MODERATOR,
        ADMIN,
        OWNER
    }

}