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

    lateinit var name: String
    lateinit var coloredName: String
    lateinit var authors: List<UUID>
    lateinit var startLocation: Location
    lateinit var baseStack: ItemStack

}