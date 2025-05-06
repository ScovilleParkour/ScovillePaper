package dev.meluhdy.scoville.core.course.courses

import dev.meluhdy.scoville.core.course.AbstractCourse
import java.util.UUID

class RankupCourse(uuid: UUID = UUID.randomUUID(), timeCreated: Long = System.currentTimeMillis()) : AbstractCourse(uuid) {

    enum class Rank(val color: Char) {
        BELL            ('2'),
        PEPPERONCINI    ('2'),
        ANAHEIM         ('2'),
        POBLANO         ('a'),
        GUAJILLO        ('a'),
        JALAPENO        ('e'),
        SERRANO         ('e'),
        MANZANO         ('e'),
        CAYENNE         ('6'),
        THAI            ('6'),
        DATIL           ('6')
    }

    lateinit var rank: Rank

}