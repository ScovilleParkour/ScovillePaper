package dev.meluhdy.scoville.core.course.courses

import dev.meluhdy.scoville.core.course.AbstractCourse
import java.util.UUID

class RankupCourse(uuid: UUID = UUID.randomUUID(), timeCreated: Long = System.currentTimeMillis()) : AbstractCourse(uuid) {

    enum class Rank(val color: Char) {
        UNKNOWN         ('5'),
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

    override var authors: List<UUID> = mutableListOf<UUID>(UUID.fromString("1226cf17-80ff-402e-9559-d54384148a33"))
    var rank: Rank = Rank.UNKNOWN
    override var courseType = CourseType.RANKUP

}