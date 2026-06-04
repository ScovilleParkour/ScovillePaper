package dev.meluhdy.scoville.core.parkourer

import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.melodia.utils.FileUtils
import dev.meluhdy.melodia.utils.FileUtils.requireString
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.serialization.parkourer.ParkourerSerializer
import kotlinx.serialization.json.JsonElement
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

object ParkourerManager: MelodiaSavingManager<Parkourer>() {

    val baseFolder: String
        get() = FileUtils.getFile(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("player_folder")).absolutePath

    fun get(player: Player): Parkourer? = get(player.uniqueId)

    fun getOrCreate(player: Player, factory: () -> Parkourer): Parkourer = getOrCreate(player.uniqueId, factory)

    override fun getFile(obj: Parkourer): File = File(baseFolder, "${obj.uuid}.json")

    override fun loadSaves(): Array<File> = File(baseFolder).listFiles() ?: arrayOf()

    override fun serializeObject(obj: Parkourer): JsonElement = serializer.encodeToJsonElement(ParkourerSerializer, obj)

    override fun deserializeObject(jsonElement: JsonElement): Parkourer = serializer.decodeFromJsonElement(ParkourerSerializer, jsonElement)

}