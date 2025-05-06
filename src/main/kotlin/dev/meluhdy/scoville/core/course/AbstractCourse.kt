package dev.meluhdy.scoville.core.course

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.scoville.core.course.courses.OneJumpCourse
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.core.course.courses.UserCourse
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.UUID
import kotlin.reflect.KClass

abstract class AbstractCourse(uuid: UUID = UUID.randomUUID(), val timeCreated: Long = System.currentTimeMillis()) : MelodiaItem(uuid) {

    enum class CourseType(val courseClass: KClass<out AbstractCourse>) {
        USER(UserCourse::class),
        RANKUP(RankupCourse::class),
        ONEJUMP(OneJumpCourse::class)
    }

    open lateinit var name: String
    open lateinit var coloredName: String
    open lateinit var authors: List<UUID>
    open lateinit var startLocation: Location
    open lateinit var baseStack: ItemStack
    open lateinit var courseType: CourseType

}