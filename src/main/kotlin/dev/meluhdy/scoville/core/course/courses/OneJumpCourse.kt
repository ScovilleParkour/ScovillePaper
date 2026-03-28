package dev.meluhdy.scoville.core.course.courses

import dev.meluhdy.scoville.core.course.AbstractCourse
import java.util.UUID

class OneJumpCourse(uuid: UUID = UUID.randomUUID(), timeCreated: Long = System.currentTimeMillis()) : AbstractCourse(uuid) {

    enum class OJDifficulty(val color: Char) {
        D1('b'),
        D2('a'),
        D3('e'),
        D4('6'),
        D5('d'),
        D6('c'),
        D7('5'),
        D8('9'),
        D9('3'),
        D10('8'),
        D11('f'),
        UNKNOWN('5')
    }

    var difficulty: OJDifficulty = OJDifficulty.UNKNOWN
    override var courseType = CourseType.ONEJUMP
    var jumps: Int = 0

}