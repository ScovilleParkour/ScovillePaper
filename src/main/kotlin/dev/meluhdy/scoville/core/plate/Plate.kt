package dev.meluhdy.scoville.core.plate

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import org.bukkit.Location
import java.util.UUID

class Plate(uuid: UUID) : MelodiaItem(uuid) {

    enum class PlateType {
        BEGIN,
        END;
    }

    private var course: UUID? = null
    var location: Location? = null

    fun getCourse(): AbstractCourse? = course?.let { CourseManager.get(it) }

    fun setCourse(uuid: UUID) { this.course = uuid }
    fun setCourse(course: AbstractCourse) = this.setCourse(course.uuid)

}