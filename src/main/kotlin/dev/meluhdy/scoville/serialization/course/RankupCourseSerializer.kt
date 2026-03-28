package dev.meluhdy.scoville.serialization.course

import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass

class RankupCourseSerializer: AbstractCourseSerializer<RankupCourse>() {

    class RankupCourseBuilder: AbstractCourseBuilder<RankupCourse>() {

        override val clazz: KClass<RankupCourse> = RankupCourse::class

        var rank: RankupCourse.Rank = RankupCourse.Rank.UNKNOWN
        override var type: AbstractCourse.CourseType = AbstractCourse.CourseType.RANKUP

        override fun extraSteps(course: RankupCourse) {
            course.rank = rank
        }

    }

    override fun extraSteps(): Array<SerializerElement<*, RankupCourse>> = arrayOf(
        SerializerElement("rank", Int.serializer(), { it.rank.ordinal }, { diff, builder -> (builder as RankupCourseBuilder).rank =
            RankupCourse.Rank.entries[diff] })
    )

    override val builder: Builder<RankupCourse> = RankupCourseBuilder()

}