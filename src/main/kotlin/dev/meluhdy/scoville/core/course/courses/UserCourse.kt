package dev.meluhdy.scoville.core.course.courses

import dev.meluhdy.scoville.core.course.AbstractCourse
import java.util.UUID

class UserCourse(uuid: UUID = UUID.randomUUID(), timeCreated: Long = System.currentTimeMillis()) : AbstractCourse(uuid) {

    enum class Difficulty() {
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

    lateinit var difficulty: Difficulty
    override var courseType = CourseType.USER

}