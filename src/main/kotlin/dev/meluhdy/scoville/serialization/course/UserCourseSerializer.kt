package dev.meluhdy.scoville.serialization.course

import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.courses.UserCourse
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass

class UserCourseSerializer: AbstractCourseSerializer<UserCourse>() {

    class UserCourseBuilder: AbstractCourseBuilder<UserCourse>() {

        override val clazz: KClass<UserCourse> = UserCourse::class
        override var type: AbstractCourse.CourseType = AbstractCourse.CourseType.USER

        var difficulty: UserCourse.Difficulty = UserCourse.Difficulty.UNKNOWN
        var tag: String? = null

        override fun extraSteps(course: UserCourse) {
            course.difficulty = difficulty
            course.tag = tag
        }

    }

    override fun extraSteps(): Array<SerializerElement<*, UserCourse>> = arrayOf(
        SerializerElement("difficulty", Int.serializer(), { it.difficulty.ordinal }, { diff, builder -> (builder as UserCourseBuilder).difficulty = UserCourse.Difficulty.entries[diff] }),
        SerializerElement("tag", String.serializer().nullable, { it.tag }, { value, builder -> (builder as UserCourseBuilder).tag = value })
    )

    override fun getBuilder(): Builder<UserCourse> = UserCourseBuilder()

}