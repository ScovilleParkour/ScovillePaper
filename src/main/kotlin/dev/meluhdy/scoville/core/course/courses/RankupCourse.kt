package dev.meluhdy.scoville.core.course.courses

import dev.meluhdy.scoville.core.course.AbstractCourse
import java.util.UUID

class RankupCourse(uuid: UUID = UUID.randomUUID(), timeCreated: Long = System.currentTimeMillis()) : AbstractCourse(uuid) {

    enum class Rank(val color: Char, val displayName: String) {
        UNKNOWN         ('5', "Unknown"),
        BELL            ('2', "Bell"),
        PEPPERONCINI    ('2', "Pepperoncini"),
        ANAHEIM         ('2', "Anaheim"),
        POBLANO         ('a', "Polblano"),
        GUAJILLO        ('a', "Guajillo"),
        JALAPENO        ('e', "Jalapeno"),
        SERRANO         ('e', "Serrano"),
        MANZANO         ('e', "Manzano"),
        CAYENNE         ('6', "Cayenne"),
        THAI            ('6', "Thai"),
        DATIL           ('6', "Datil");

        companion object
    }

    override var authors: List<UUID> = mutableListOf<UUID>(UUID.fromString("1226cf17-80ff-402e-9559-d54384148a33"))
    var rank: Rank = Rank.UNKNOWN
    override var courseType = CourseType.RANKUP

}