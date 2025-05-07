package dev.meluhdy.scoville.core.course

import dev.meluhdy.melodia.manager.MelodiaItem
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.UUID

abstract class AbstractCourse(uuid: UUID = UUID.randomUUID(), val timeCreated: Long = System.currentTimeMillis()) : MelodiaItem(uuid) {

    enum class CourseType() {
        USER,
        RANKUP,
        ONEJUMP
    }

    open lateinit var name: String
    open lateinit var coloredName: String
    open lateinit var authors: List<UUID>
    open lateinit var startLocation: Location
    open lateinit var baseStack: ItemStack
    open lateinit var courseType: CourseType

}