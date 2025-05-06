package dev.meluhdy.scoville.serialization.course

import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass

class RankupCourseSerializer: AbstractCourseSerializer<RankupCourse>() {

    class RankupCourseBuilder: AbstractCourseBuilder<RankupCourse>() {

        override val clazz: KClass<RankupCourse> = RankupCourse::class

        lateinit var rank: RankupCourse.Rank

        override fun extraSteps(course: RankupCourse) {
            course.rank = rank
        }

    }

    override fun extraSteps(): Array<SerializerElement<*, RankupCourse>> = arrayOf(
        SerializerElement<Int, RankupCourse>("rank", Int.serializer(), { it.rank.ordinal }, { diff, builder -> (builder as RankupCourseBuilder).rank =
            RankupCourse.Rank.entries[diff] })
    )

    override val builder: Builder<RankupCourse> = RankupCourseBuilder()

}