package dev.meluhdy.scoville.core.plate

import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.serialization.plate.PlateSerializer
import kotlinx.serialization.json.JsonElement
import java.io.File

object PlateManager : MelodiaSavingManager<Plate>() {

    val baseFolder
        get() = "${Scoville.plugin.dataFolder}${File.separator}plates"

    fun getPlates(course: AbstractCourse) = get { plate -> plate.getCourse() == course }

    override fun getFile(obj: Plate): File = File(baseFolder, "${obj.uuid}.json")

    override fun loadSaves(): Array<File> = File(baseFolder).listFiles() ?: arrayOf()

    override fun serializeObject(obj: Plate): JsonElement = serializer.encodeToJsonElement(PlateSerializer, obj)

    override fun deserializeObject(jsonElement: JsonElement): Plate = serializer.decodeFromJsonElement(PlateSerializer, jsonElement)

}