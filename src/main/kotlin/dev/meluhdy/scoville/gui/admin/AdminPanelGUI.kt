package dev.meluhdy.scoville.gui.admin

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scoville.gui.admin.course.CourseAdminGUI
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class AdminPanelGUI(p: Player, pg: MelodiaGUI): MelodiaGUI(Scoville.plugin, p, pg), IScovilleGUI {

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.admin.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        MelodiaGUIItem(20, ItemUtils.createItem(Material.ENDER_EYE, 1,
            getTitle(p, TranslatedString("menu.admin.courses.title", arrayOf())),
            *getDesc(p, TranslatedString("menu.admin.courses.desc", arrayOf()))
        )) {
            CourseAdminGUI(p, this).open()
        },
        MelodiaGUIItem(22, ItemUtils.createSkull(p.uniqueId, 1,
            getTitle(p, TranslatedString("menu.admin.players.title", arrayOf())),
            *getDesc(p, TranslatedString("menu.admin.players.desc", arrayOf()))
        )) {
            it.whoClicked.sendMessage("Players")
        },
        MelodiaGUIItem(24, ItemUtils.createItem(Material.OAK_SAPLING, 1,
            getTitle(p, TranslatedString("menu.admin.seasons.title", arrayOf())),
            *getDesc(p, TranslatedString("menu.admin.seasons.desc", arrayOf()))
        )) {
            it.whoClicked.sendMessage("Seasons")
        },
        getBack(36, this)
    )

    override fun extraItems() {
        createBorder(this)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}