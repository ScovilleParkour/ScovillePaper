package dev.meluhdy.scoville.serialization.course

import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.courses.UserCourse
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass

class UserCourseSerializer: AbstractCourseSerializer<UserCourse>() {

    class UserCourseBuilder: AbstractCourseBuilder<UserCourse>() {

        override val clazz: KClass<UserCourse> = UserCourse::class
        override var type: AbstractCourse.CourseType = AbstractCourse.CourseType.USER

        var difficulty: UserCourse.Difficulty = UserCourse.Difficulty.UNKNOWN

        override fun extraSteps(course: UserCourse) {
            course.difficulty = difficulty
        }

    }

    override fun extraSteps(): Array<SerializerElement<*, UserCourse>> = arrayOf(
        SerializerElement("difficulty", Int.serializer(), { it.difficulty.ordinal }, { diff, builder -> (builder as UserCourseBuilder).difficulty = UserCourse.Difficulty.entries[diff] })
    )

    override fun getBuilder(): Builder<UserCourse> = UserCourseBuilder()

}