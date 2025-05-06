package dev.meluhdy.scoville.serialization.course

import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.core.course.courses.OneJumpCourse
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass

class OneJumpCourseSerializer: AbstractCourseSerializer<OneJumpCourse>() {

    class OneJumpCourseBuilder: AbstractCourseBuilder<OneJumpCourse>() {

        override val clazz: KClass<OneJumpCourse> = OneJumpCourse::class

        lateinit var difficulty: OneJumpCourse.OJDifficulty
        var jumps: Int = 0

        override fun extraSteps(course: OneJumpCourse) {
            course.difficulty = difficulty
            course.jumps = jumps
        }

    }

    override fun extraSteps(): Array<SerializerElement<*, OneJumpCourse>> = arrayOf(
        SerializerElement<Int, OneJumpCourse>("difficulty", Int.serializer(), { it.difficulty.ordinal }, { diff, builder -> (builder as OneJumpCourseBuilder).difficulty =
            OneJumpCourse.OJDifficulty.entries[diff] }),
        SerializerElement<Int, OneJumpCourse>("jumps", Int.serializer(), { it.jumps }, { jumps, builder -> (builder as OneJumpCourseBuilder).jumps = jumps })
    )

    override val builder: Builder<OneJumpCourse> = OneJumpCourseBuilder()

}