package dev.meluhdy.scoville

import dev.meluhdy.melodia.MelodiaPlugin
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.melodia.utils.ConsoleLogger
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.LoggingUtils
import dev.meluhdy.melodia.utils.TranslationFolder
import dev.meluhdy.scoville.achievement.AchievementManager
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.course.courses.UserCourse
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.event.listener.AchievementBroadcastListener
import dev.meluhdy.scoville.event.listener.CreateParkourerListener
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.Listener
import org.bukkit.inventory.meta.SkullMeta
import java.util.Locale
import java.util.UUID

class Scoville : MelodiaPlugin() {

    companion object {
        lateinit var plugin: MelodiaPlugin
    }

    override val melodiaCommands: Array<MelodiaCommand> = arrayOf()

    override val resourceFiles: Array<String> = arrayOf(
        "lang/en.properties",
        "lang/de.properties",
        "lang/ja.properties",
        "lang/pl.properties"
    )

    override val listeners: Array<Listener> = arrayOf(
        AchievementBroadcastListener,
        CreateParkourerListener
    )

    override val translationFolder: TranslationFolder = TranslationFolder("lang", Locale.of("en"))

    override val logger: ConsoleLogger = ConsoleLogger("Scoville", LoggingUtils.ConsoleLevel.DEBUG)

    override val savingManagers: Array<MelodiaSavingManager<*>> = arrayOf(
        ParkourerManager
    )

    override fun onLoad() {
        plugin = this
    }

    override fun onEnable() {

        super.onEnable()

        AchievementManager

    }

}
