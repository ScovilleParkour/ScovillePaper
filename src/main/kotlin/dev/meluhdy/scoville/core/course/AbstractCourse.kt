package dev.meluhdy.scoville.core.course

import dev.meluhdy.melodia.manager.MelodiaItem
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.UUID

abstract class AbstractCourse(uuid: UUID = UUID.randomUUID(), val timeCreated: Long = System.currentTimeMillis()) : MelodiaItem(uuid) {

    enum class CourseType() {
        UNKNOWN,
        USER,
        RANKUP,
        ONEJUMP
    }

    open var name: String? = null
    open var coloredName: String? = null
    open var authors: List<UUID> = listOf()
    open var startLocation: Location? = null
    open var baseStack: ItemStack? = null
    open var courseType: CourseType = CourseType.UNKNOWN

}