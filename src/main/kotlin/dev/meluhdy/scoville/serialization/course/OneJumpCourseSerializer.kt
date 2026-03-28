package dev.meluhdy.scoville.serialization.course

import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.courses.OneJumpCourse
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass

class OneJumpCourseSerializer: AbstractCourseSerializer<OneJumpCourse>() {

    class OneJumpCourseBuilder: AbstractCourseBuilder<OneJumpCourse>() {

        override val clazz: KClass<OneJumpCourse> = OneJumpCourse::class

        var difficulty: OneJumpCourse.OJDifficulty = OneJumpCourse.OJDifficulty.UNKNOWN
        override var type: AbstractCourse.CourseType = AbstractCourse.CourseType.ONEJUMP
        var jumps: Int = 0

        override fun extraSteps(course: OneJumpCourse) {
            course.difficulty = difficulty
            course.jumps = jumps
        }

    }

    override fun extraSteps(): Array<SerializerElement<*, OneJumpCourse>> = arrayOf(
        SerializerElement("difficulty", Int.serializer(), { it.difficulty.ordinal }, { diff, builder -> (builder as OneJumpCourseBuilder).difficulty =
            OneJumpCourse.OJDifficulty.entries[diff] }),
        SerializerElement("jumps", Int.serializer(), { it.jumps }, { jumps, builder -> (builder as OneJumpCourseBuilder).jumps = jumps })
    )

    override val builder: Builder<OneJumpCourse> = OneJumpCourseBuilder()

}