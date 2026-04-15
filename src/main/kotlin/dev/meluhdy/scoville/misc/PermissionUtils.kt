package dev.meluhdy.scoville.misc

import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.core.parkourer.Parkourer
import dev.meluhdy.scoville.misc.PermissionUtils.RANK_PREFIX
import dev.meluhdy.scoville.misc.PermissionUtils.ensureGroup
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.group.Group
import net.luckperms.api.model.user.User
import net.luckperms.api.node.types.InheritanceNode
import net.luckperms.api.track.Track
import org.bukkit.entity.Player
import java.util.stream.Collectors

fun Track.getCurrentGroup(user: User): String? {
    return user.getInheritedGroups(user.queryOptions)
        .map { it.name }
        .maxByOrNull { this.groups.indexOf(it) }
}

object PermissionUtils {

    private const val RANK_PREFIX = "group.rank."

    private val luckPerms = LuckPermsProvider.get()

    private fun ensureGroup(group: String): Group = luckPerms.groupManager.getGroup(group) ?: luckPerms.groupManager.createAndLoadGroup(group).get()

    private fun ensureTrack(track: String): Track = luckPerms.trackManager.getTrack(track) ?: luckPerms.trackManager.createAndLoadTrack(track).get()

    fun RankupCourse.Rank.asGroup() : Group {
        return ensureGroup(RANK_PREFIX + this.name.lowercase())
    }

    fun RankupCourse.Rank.Companion.fromGroup(group: Group) : RankupCourse.Rank? {
        val name = group.name
        if (!name.startsWith(RANK_PREFIX)) return null

        return RankupCourse.Rank.entries.find { it.name == name.removePrefix(RANK_PREFIX).uppercase() }
    }

    private fun ensureRankTrack(): Track {
        val track = this.ensureTrack("track.rank")

        RankupCourse.Rank.entries.forEach {
            if (it == RankupCourse.Rank.UNKNOWN) return@forEach
            val group = it.asGroup()
            if (!track.containsGroup(group)) track.appendGroup(group)
        }
        return track
    }

    fun getRank(p: Parkourer): RankupCourse.Rank {
        val track = this.ensureRankTrack()
        val user = luckPerms.userManager.getUser(p.uuid) ?: return RankupCourse.Rank.UNKNOWN
        val currGroup = ensureGroup(track.getCurrentGroup(user) ?: return RankupCourse.Rank.UNKNOWN)

        return RankupCourse.Rank.fromGroup(currGroup) ?: RankupCourse.Rank.UNKNOWN
    }

    fun setRank(p: Parkourer, rank: RankupCourse.Rank) {
        val track = this.ensureRankTrack()
        val user = luckPerms.userManager.getUser(p.uuid) ?: return

        val currGroup = track.getCurrentGroup(user)
        val newGroup = rank.asGroup()

        luckPerms.userManager.modifyUser(p.uuid) { user ->
            if (currGroup != null)
                user.data().remove(InheritanceNode.builder(currGroup).build())
            user.data().add(InheritanceNode.builder(newGroup).build())
        }
    }

}