package dev.meluhdy.scoville.core.course

import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.scoville.Scoville
import kotlinx.serialization.json.JsonElement
import java.io.File

object CourseManager : MelodiaSavingManager<AbstractCourse>() {

    override fun getFile(obj: AbstractCourse): File = File(Scoville.plugin.dataFolder, "courses/${obj.uuid}.json")

    override fun loadSaves(): Array<File> = arrayOf(File(Scoville.plugin.dataFolder, "courses"))

    override fun serializeObject(obj: AbstractCourse): JsonElement {
        TODO("Not yet implemented")
    }

    override fun deserializeObject(jsonElement: JsonElement): AbstractCourse {
        TODO("Not yet implemented")
    }

}