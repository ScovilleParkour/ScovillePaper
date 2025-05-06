package dev.meluhdy.scoville.core.serialization.courses

import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.core.course.courses.UserCourse
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass

class UserCourseSerializer: AbstractCourseSerializer<UserCourse>() {

    class UserCourseBuilder: AbstractCourseBuilder<UserCourse>() {

        override val clazz: KClass<UserCourse> = UserCourse::class

        lateinit var difficulty: UserCourse.Difficulty

        override fun extraSteps(course: UserCourse) {
            course.difficulty = difficulty
        }

    }

    override fun extraSteps(): Array<SerializerElement<*, UserCourse>> = arrayOf(
        SerializerElement<Int, UserCourse>("difficulty", Int.serializer(), { it.difficulty.ordinal }, { diff, builder -> (builder as UserCourseBuilder).difficulty = UserCourse.Difficulty.entries[diff] })
    )

    override val builder: Builder<UserCourse> = UserCourseBuilder()

}