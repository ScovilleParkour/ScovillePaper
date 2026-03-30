package dev.meluhdy.scoville.serialization.course

import dev.meluhdy.melodia.misc.serialization.ItemStackSerializer
import dev.meluhdy.melodia.misc.serialization.LocationSerializer
import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.melodia.misc.serialization.UUIDSerializer
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.courses.OneJumpCourse
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.UUID
import kotlin.reflect.KClass

abstract class AbstractCourseSerializer<T: AbstractCourse>: MelodiaSerializer<T>() {

    companion object {
        fun getSerializer(course: AbstractCourse): MelodiaSerializer<out AbstractCourse> = when (course) {
            is RankupCourse -> RankupCourseSerializer()
            is OneJumpCourse -> OneJumpCourseSerializer()
            else -> UserCourseSerializer()
        }

        fun getSerializer(element: JsonElement): MelodiaSerializer<out AbstractCourse> = when(AbstractCourse.CourseType.entries[element.jsonObject["type"]?.jsonPrimitive?.int!!]) {
            AbstractCourse.CourseType.UNKNOWN -> throw IllegalArgumentException("Trying to serialize unknown course type!")
            AbstractCourse.CourseType.USER -> UserCourseSerializer()
            AbstractCourse.CourseType.RANKUP -> RankupCourseSerializer()
            AbstractCourse.CourseType.ONEJUMP -> OneJumpCourseSerializer()
        }
    }

    abstract class AbstractCourseBuilder<T: AbstractCourse>: Builder<T>() {

        var name: String? = null
        var coloredName: String? = null
        var authors: List<UUID> = listOf()
        var startLocation: Location? = null
        var baseStack: ItemStack? = null
        abstract var type: AbstractCourse.CourseType
        var timeCreated: Long = 0

        abstract val clazz: KClass<T>

        override fun build(): T {
            val course = clazz.constructors.first().call(uuid, timeCreated)
            course.name = name
            course.coloredName = coloredName
            course.authors = authors
            course.startLocation = startLocation
            course.baseStack = baseStack
            course.courseType = type
            extraSteps(course)
            return course
        }

        abstract fun extraSteps(course: T)

    }

    override val steps: Array<SerializerElement<*, T>>
        get() {
            val out = arrayListOf<SerializerElement<*, T>>()
            out.add(SerializerElement<String?, T>("name", String.serializer().nullable, { it.name }, { name, builder -> (builder as AbstractCourseBuilder).name = name }))
            out.add(SerializerElement<String?, T>("coloredName", String.serializer().nullable, { it.coloredName }, { coloredName, builder -> (builder as AbstractCourseBuilder).coloredName = coloredName }))
            out.add(SerializerElement<List<UUID>, T>("authors", ListSerializer(UUIDSerializer()), { it.authors }, { authors, builder -> (builder as AbstractCourseBuilder).authors = authors }))
            out.add(SerializerElement<Location?, T>("startLoc", LocationSerializer().nullable, { it.startLocation }, { loc, builder -> (builder as AbstractCourseBuilder).startLocation = loc }))
            out.add(SerializerElement<ItemStack?, T>("baseStack", ItemStackSerializer().nullable, { it.baseStack }, { baseStack, builder -> (builder as AbstractCourseBuilder).baseStack = baseStack }))
            out.add(SerializerElement<Long, T>("timeCreated", Long.serializer(), { it.timeCreated }, { timeCreated, builder -> (builder as AbstractCourseBuilder).timeCreated = timeCreated }))
            out.add(SerializerElement<Int, T>("type", Int.serializer(), { it.courseType.ordinal }, { type, builder -> (builder as AbstractCourseBuilder).type = AbstractCourse.CourseType.entries[type] }))
            out.addAll(extraSteps())
            return out.toTypedArray()
        }

    abstract fun extraSteps(): Array<SerializerElement<*, T>>

}