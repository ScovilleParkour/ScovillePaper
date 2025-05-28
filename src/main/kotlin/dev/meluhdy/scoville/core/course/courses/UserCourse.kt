package dev.meluhdy.scoville.core.course.courses

import dev.meluhdy.scoville.core.course.AbstractCourse
import java.util.UUID

class UserCourse(uuid: UUID = UUID.randomUUID(), timeCreated: Long = System.currentTimeMillis()) : AbstractCourse(uuid) {

    enum class Difficulty() {
        UNKNOWN,
        SWEET,
        TANGY,
        SAVORY,
        ZESTY,
        SPICY,
        HOT,
        SIZZLING,
        FIERY,
        SCORCHING,
        BLAZING
    }

    var difficulty: Difficulty = Difficulty.UNKNOWN
    override var courseType = CourseType.USER

}