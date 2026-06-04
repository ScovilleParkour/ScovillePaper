package dev.meluhdy.scoville.core.course

import dev.meluhdy.melodia.manager.MelodiaItem
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.UUID

abstract class AbstractCourse(uuid: UUID = UUID.randomUUID(), val timeCreated: Long = System.currentTimeMillis()) : MelodiaItem(uuid) {

    enum class CourseType(val str: String) {
        UNKNOWN     ("unknown"),
        USER        ("user"),
        RANKUP      ("rankup"),
        ONEJUMP     ("oj")
    }

    open var name: String? = null
    open var coloredName: String? = null
    open var authors: List<UUID> = listOf()
    open var startLocation: Location? = null
    open var baseStack: ItemStack? = null
    open var courseType: CourseType = CourseType.UNKNOWN

    override fun equals(other: Any?): Boolean {
        return other is AbstractCourse && uuid == other.uuid
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + timeCreated.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (coloredName?.hashCode() ?: 0)
        result = 31 * result + authors.hashCode()
        result = 31 * result + (startLocation?.hashCode() ?: 0)
        result = 31 * result + (baseStack?.hashCode() ?: 0)
        result = 31 * result + courseType.hashCode()
        return result
    }

}