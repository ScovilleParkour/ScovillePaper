package dev.meluhdy.scoville.misc

import dev.meluhdy.scoville.core.course.courses.RankupCourse
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.group.Group
import net.luckperms.api.model.user.User
import net.luckperms.api.node.types.InheritanceNode
import net.luckperms.api.track.Track
import org.bukkit.entity.Player

fun Track.getCurrentGroup(user: User): String? {
    return user.getInheritedGroups(user.queryOptions)
        .map { it.name }
        .maxByOrNull { this.groups.indexOf(it) }
}

abstract class Tracked<T: Enum<*>> {

    companion object {
        val luckPerms = LuckPermsProvider.get()

        protected fun ensureGroup(group: String): Group = luckPerms.groupManager.getGroup(group) ?: luckPerms.groupManager.createAndLoadGroup(group).get()
        protected fun ensureTrack(track: String): Track = luckPerms.trackManager.getTrack(track) ?: luckPerms.trackManager.createAndLoadTrack(track).get()
    }

    abstract val prefix: String
    abstract val trackName: String
    protected abstract val default: T

    private var ensured: Boolean = false

    abstract fun getEntries(): List<T>

    protected abstract fun toGroupRoot(obj: T): String
    protected abstract fun fromGroupRoot(str: String): T?

    fun asGroup(obj: T): Group = ensureGroup("${prefix.removeSuffix(".")}." + toGroupRoot(obj))

    fun fromGroup(group: Group): T? {
        val name = group.name
        if (!name.startsWith(prefix)) return null

        return fromGroupRoot(name.removePrefix("${prefix.removeSuffix(".")}."))
    }

    private fun ensureTTrack(): Track {
        val track = Tracked.ensureTrack(trackName)

        if (!ensured) {
            track.clearGroups()

            getEntries()
                .sortedBy { it.ordinal }
                .map { asGroup(it) }
                .forEach { track.appendGroup(it) }
            luckPerms.trackManager.saveTrack(track)
            ensured = true
        }
        return track
    }

    fun fromPlayer(p: Player): T {
        val track = this.ensureTTrack()
        val user = luckPerms.userManager.getUser(p.uniqueId) ?: return default
        val currGroup: Group
        val curr = track.getCurrentGroup(user)
        if (curr == null || curr == "default") {
            currGroup = asGroup(default)
            setGroup(p, default)
        } else {
            currGroup = ensureGroup(curr)
        }

        return fromGroup(currGroup) ?: default
    }

    fun setGroup(p: Player, obj: T) {
        val track = this.ensureTTrack()
        val user = luckPerms.userManager.getUser(p.uniqueId) ?: return

        val currGroup = track.getCurrentGroup(user)
        val newGroup = asGroup(obj)

        if (currGroup != null && currGroup != "default")
            user.data().remove(InheritanceNode.builder(currGroup).build())
        user.data().add(InheritanceNode.builder(newGroup).build())

        luckPerms.userManager.saveUser(user)
    }

}