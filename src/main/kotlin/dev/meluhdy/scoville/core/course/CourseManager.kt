package dev.meluhdy.scoville.core.course

import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.utils.FileUtils
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.courses.OneJumpCourse
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.core.course.courses.UserCourse
import dev.meluhdy.scoville.serialization.course.AbstractCourseSerializer
import kotlinx.serialization.json.JsonElement
import java.io.File

object CourseManager : MelodiaSavingManager<AbstractCourse>() {

    val baseFolder: String
        get() = FileUtils.getFile(Scoville.plugin, "courses").absolutePath

    override fun getFile(obj: AbstractCourse): File = when (obj) {
        is OneJumpCourse -> File("$baseFolder${File.separator}oj", "${obj.uuid}.json")
        is RankupCourse -> File("$baseFolder${File.separator}rankup", "${obj.uuid}.json")
        is UserCourse -> File("$baseFolder${File.separator}user", "${obj.uuid}.json")
        else -> File(baseFolder, "${obj.uuid}.json")
    }

    override fun loadSaves(): Array<File> {
        val out = ArrayList<File>()
        out.addAll(File(baseFolder, "oj").listFiles() ?: arrayOf())
        out.addAll(File(baseFolder, "rankup").listFiles() ?: arrayOf())
        out.addAll(File(baseFolder, "user").listFiles() ?: arrayOf())
        return out.toTypedArray()
    }

    override fun serializeObject(obj: AbstractCourse): JsonElement = serializer.encodeToJsonElement(
        AbstractCourseSerializer.getSerializer(obj) as MelodiaSerializer<AbstractCourse>, obj)

    override fun deserializeObject(jsonElement: JsonElement): AbstractCourse = serializer.decodeFromJsonElement(
        AbstractCourseSerializer.getSerializer(jsonElement) as MelodiaSerializer<AbstractCourse>, jsonElement)

    fun get(name: String): AbstractCourse? {
        return this.get { course -> course.name == name }
    }

}