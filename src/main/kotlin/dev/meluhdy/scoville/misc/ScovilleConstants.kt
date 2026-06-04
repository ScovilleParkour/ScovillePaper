package dev.meluhdy.scoville.misc

import dev.meluhdy.melodia.utils.BlockDataUtils.getFromTag
import dev.meluhdy.melodia.utils.BlockDataUtils.setTag
import dev.meluhdy.melodia.utils.FileUtils.requireString
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.configuration.file.YamlConfiguration
import java.util.UUID

object ScovilleConstants {

    val COURSE_KEY = NamespacedKey(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("course_key"))
    val LOCATION_KEY = NamespacedKey(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("location_key"))
    val TAG_KEY = NamespacedKey(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("tag_key"))

    fun Block.getScovilleLocation(): Location? = this.getFromTag(Scoville.plugin, LOCATION_KEY)
    fun Block.setScovilleLocation(location: Location) = this.setTag(Scoville.plugin, LOCATION_KEY, location)

    fun Block.getScovilleCourseId(): UUID? = this.getFromTag(Scoville.plugin, COURSE_KEY)
    fun Block.getScovilleCourse(): AbstractCourse? = this.getScovilleCourseId()?.let { CourseManager.get(it) }
    fun Block.setScovilleCourse(course: AbstractCourse) = this.setScovilleCourse(course.uuid)
    fun Block.setScovilleCourse(uuid: UUID) = this.setTag(Scoville.plugin, COURSE_KEY, uuid)

}