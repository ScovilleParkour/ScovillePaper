package dev.meluhdy.scoville.core.course.courses

import dev.meluhdy.scoville.core.course.AbstractCourse
import java.util.UUID

class UserCourse(uuid: UUID = UUID.randomUUID(), timeCreated: Long = System.currentTimeMillis()) : AbstractCourse(uuid) {

    enum class Difficulty(val color: Char) {
        UNKNOWN         ('5'),
        SWEET           ('2'),
        TANGY           ('a'),
        SAVORY          ('a'),
        ZESTY           ('e'),
        SPICY           ('e'),
        HOT             ('6'),
        SIZZLING        ('6'),
        FIERY           ('c'),
        SCORCHING       ('c'),
        BLAZING         ('4')
    }

    var difficulty: Difficulty = Difficulty.UNKNOWN
    var tag: String? = null
    override var courseType = CourseType.USER

}