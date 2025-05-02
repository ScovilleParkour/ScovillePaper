package dev.meluhdy.scoville

import dev.meluhdy.melodia.MelodiaPlugin
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TextUtils.translate
import dev.meluhdy.melodia.utils.TextUtils.translateList
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.TranslationFolder
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.achievement.AchievementManager
import dev.meluhdy.scoville.event.listener.AchievementBroadcastListener
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.Listener
import java.util.Locale

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

    override fun onEnable() {

        super.onEnable()

        plugin = this

        AchievementManager
    }

}
