package dev.meluhdy.scoville

import dev.meluhdy.melodia.MelodiaPlugin
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.ConsoleLogger
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.LoggingUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TextUtils.translate
import dev.meluhdy.melodia.utils.TextUtils.translateList
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.TranslationFolder
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.achievement.AchievementManager
import dev.meluhdy.scoville.core.course.courses.UserCourse
import dev.meluhdy.scoville.core.serialization.courses.UserCourseSerializer
import dev.meluhdy.scoville.event.listener.AchievementBroadcastListener
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.Locale
import java.util.UUID

class Scoville : MelodiaPlugin() {

    companion object {
        lateinit var plugin: MelodiaPlugin
    }

    override val melodiaCommands: ArrayList<MelodiaCommand> = arrayListOf()

    override val resourceFiles: ArrayList<String> = arrayListOf(
        "lang/en.properties",
        "lang/de.properties",
        "lang/ja.properties",
        "lang/pl.properties"
    )

    override val listeners: ArrayList<Listener> = arrayListOf(
        AchievementBroadcastListener
    )
    override val translationFolder: TranslationFolder = TranslationFolder("lang", Locale.of("en"))

    override val logger: ConsoleLogger = ConsoleLogger("Scoville", LoggingUtils.ConsoleLevel.DEBUG)

    override fun onEnable() {

        super.onEnable()

        plugin = this

        val course = UserCourse()
        course.name = "Kyouki"
        course.coloredName = "&4&lKyouki"
        course.difficulty = UserCourse.Difficulty.FIERY
        course.authors = arrayListOf(UUID.fromString("9334beef-efab-4194-900d-d23e1a721c0e"))
        course.startLocation = Location(Bukkit.getWorlds()[0], 0.0, 10.0, 20.0, 30.0f, 40.0f)
        course.baseStack = ItemUtils.createSkull("http://textures.minecraft.net/texture/99c6a378a72da175831c9394fa45eb7343f31d7d058fa76a9a646be007b7888d")

        val json = Json.encodeToString(UserCourseSerializer(), course)
        logger.info(json)

        val newCourse = Json.decodeFromString(UserCourseSerializer(), json)
        logger.info("${newCourse.name} has item ${newCourse.baseStack.type}")
        if (newCourse.baseStack.itemMeta is SkullMeta) logger.info("Skull URL: ${(newCourse.baseStack.itemMeta as SkullMeta).playerProfile!!.textures.skin}")

        AchievementManager
    }

}
