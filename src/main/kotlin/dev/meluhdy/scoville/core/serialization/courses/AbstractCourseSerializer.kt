package dev.meluhdy.scoville.core.serialization.courses

import dev.meluhdy.melodia.misc.serialization.ItemStackSerializer
import dev.meluhdy.melodia.misc.serialization.LocationSerializer
import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.melodia.misc.serialization.UUIDSerializer
import dev.meluhdy.scoville.core.course.AbstractCourse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.UUID
import kotlin.reflect.KClass

abstract class AbstractCourseSerializer<T: AbstractCourse>: MelodiaSerializer<T>() {

    abstract class AbstractCourseBuilder<T: AbstractCourse>: Builder<T>() {

        lateinit var name: String
        lateinit var coloredName: String
        lateinit var authors: List<UUID>
        lateinit var startLocation: Location
        lateinit var baseStack: ItemStack
        var timeCreated: Long = 0

        abstract val clazz: KClass<T>

        override fun build(): T {
            val course = clazz.constructors.first().call(uuid, timeCreated)
            course.name = name
            course.coloredName = coloredName
            course.authors = authors
            course.startLocation = startLocation
            course.baseStack = baseStack
            extraSteps(course)
            return course
        }

        abstract fun extraSteps(course: T)

    }

    override val steps: Array<SerializerElement<*, T>>
        get() {
            val out = arrayListOf<SerializerElement<*, T>>()
            out.add(SerializerElement<String, T>("name", String.serializer(), { it.name }, { name, builder -> (builder as AbstractCourseBuilder).name = name }))
            out.add(SerializerElement<String, T>("coloredName", String.serializer(), { it.coloredName }, { coloredName, builder -> (builder as AbstractCourseBuilder).coloredName = coloredName }))
            out.add(SerializerElement<List<UUID>, T>("authors", ListSerializer(UUIDSerializer()), { it.authors }, { authors, builder -> (builder as AbstractCourseBuilder).authors = authors }))
            out.add(SerializerElement<Location, T>("startLoc", LocationSerializer(), { it.startLocation }, { loc, builder -> (builder as AbstractCourseBuilder).startLocation = loc }))
            out.add(SerializerElement<ItemStack, T>("baseStack", ItemStackSerializer(), { it.baseStack }, { baseStack, builder -> (builder as AbstractCourseBuilder).baseStack = baseStack }))
            out.add(SerializerElement<Long, T>("timeCreated", Long.serializer(), { it.timeCreated }, { timeCreated, builder -> (builder as AbstractCourseBuilder).timeCreated = timeCreated }))
            out.addAll(extraSteps())
            return out.toTypedArray()
        }

    abstract fun extraSteps(): Array<SerializerElement<*, T>>

}