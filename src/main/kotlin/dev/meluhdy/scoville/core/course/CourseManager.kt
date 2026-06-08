package dev.meluhdy.scoville.core.course

import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.utils.FileUtils
import dev.meluhdy.melodia.utils.FileUtils.requireString
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.serialization.course.AbstractCourseSerializer
import kotlinx.serialization.json.JsonElement
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import kotlin.io.path.Path

object CourseManager : MelodiaSavingManager<AbstractCourse>() {

    val baseFolder: String
        get() = FileUtils.getFile(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("course_folder")).absolutePath

    override fun getFile(obj: AbstractCourse): File = Path(baseFolder, obj.courseType.str, "${obj.uuid}.json").toFile()

    override fun loadSaves(): Array<File> = File(baseFolder).walkTopDown().filter { it.isFile }.toList().toTypedArray()

    override fun serializeObject(obj: AbstractCourse): JsonElement = serializer.encodeToJsonElement(AbstractCourseSerializer.getSerializer(obj) as MelodiaSerializer<AbstractCourse>, obj)

    override fun deserializeObject(jsonElement: JsonElement): AbstractCourse = serializer.decodeFromJsonElement(AbstractCourseSerializer.getSerializer(jsonElement) as MelodiaSerializer<AbstractCourse>, jsonElement)

    fun get(name: String): AbstractCourse? = this.get { course -> course.name == name }

}