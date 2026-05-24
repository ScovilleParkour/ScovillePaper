package dev.meluhdy.scoville.misc

import dev.meluhdy.melodia.utils.FileUtils.requireString
import dev.meluhdy.scoville.Scoville
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration

object ScovilleConstants {

    val COURSE_KEY = NamespacedKey(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("course_key"))
    val LOCATION_KEY = NamespacedKey(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("location_key"))
    val TAG_KEY = NamespacedKey(Scoville.plugin, (Scoville.plugin.config as YamlConfiguration).requireString("tag_key"))

}