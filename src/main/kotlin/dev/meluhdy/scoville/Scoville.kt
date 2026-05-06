package dev.meluhdy.scoville

import dev.meluhdy.melodia.MelodiaPlugin
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.melodia.utils.ConsoleLogger
import dev.meluhdy.melodia.utils.LoggingUtils
import dev.meluhdy.melodia.utils.TranslationFolder
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scoville.core.plate.PlateManager
import dev.meluhdy.scoville.event.listener.BroadcastListener
import dev.meluhdy.scoville.event.listener.ParkourerPositionerListener
import dev.meluhdy.scoville.event.listener.ParkourerUpdateListener
import org.bukkit.event.Listener
import java.util.Locale

class Scoville : MelodiaPlugin() {

    companion object {
        lateinit var plugin: MelodiaPlugin
    }

    init {
        plugin = this
    }

    override val melodiaCommands: Array<MelodiaCommand> = arrayOf()

    override val resourceFiles: Array<String> = arrayOf(
        "lang/en.properties",
        "lang/de.properties",
        "lang/ja.properties",
        "lang/pl.properties"
    )

    override val listeners: Array<Listener> = arrayOf(
        ParkourerUpdateListener,
        ParkourerPositionerListener,
        BroadcastListener
    )

    override val translationFolder: TranslationFolder = TranslationFolder("lang", Locale.of("en"))

    override val logger: ConsoleLogger = ConsoleLogger("Scoville", LoggingUtils.ConsoleLevel.DEBUG)

    override val savingManagers: Array<MelodiaSavingManager<*>> = arrayOf(
        ParkourerManager,
        CourseManager,
        PlateManager
    )

}
