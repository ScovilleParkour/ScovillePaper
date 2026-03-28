package dev.meluhdy.scoville.gui

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.Scoville
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.math.ceil

interface IScovilleGUI {

    companion object {
        val scovillePanes = arrayOf(
            Material.RED_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.LIME_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE
        )

        val ojPanes = arrayOf(
            Material.BLUE_STAINED_GLASS_PANE,
            Material.CYAN_STAINED_GLASS_PANE,
            Material.LIGHT_BLUE_STAINED_GLASS_PANE,
            Material.WHITE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE
        )
    }

    fun createRow(inv: MelodiaGUI, row: Int) = createRow(inv, row, scovillePanes)

    fun createRow(inv: MelodiaGUI, row: Int, panes: Array<Material>) {
        if (row > inv.rows) throw IndexOutOfBoundsException("Tried to create row outside of inventory!")
        val start = 9 * row
        for (i in 0..<5) {
            inv.inv.setItem(start + i, ItemUtils.createItem(panes[i], 1, getTitle("&r")))
            inv.inv.setItem(start + 8 - i, ItemUtils.createItem(panes[i], 1, getTitle("&r")))
        }
    }

    fun createBorder(inv: MelodiaGUI) = createBorder(inv, scovillePanes)

    fun createBorder(inv: MelodiaGUI, panes: Array<Material>) {
        val lastRow = inv.rows - 1
        createRow(inv, 0)
        createRow(inv, lastRow)
        for (i in 1..<ceil(inv.inv.size / 18.0).toInt()) {
            inv.inv.setItem(9 * i, ItemUtils.createItem(panes[i], 1, getTitle("&r")))
            inv.inv.setItem(9 * (lastRow - i), ItemUtils.createItem(panes[i], 1, getTitle("&r")))
            inv.inv.setItem(9 * i + 8, ItemUtils.createItem(panes[i], 1, getTitle("&r")))
            inv.inv.setItem(9 * (lastRow - i) + 8, ItemUtils.createItem(panes[i], 1, getTitle("&r")))
        }
    }

    fun getBack(slot: Int, curr: MelodiaGUI): MelodiaGUIItem = MelodiaGUIItem(slot, ItemUtils.createItem(Material.ARROW, 1,
        getTitle(curr.p, TranslatedString("menu.generic.prev.title", arrayOf()))
    )) {
        if (curr.prevGUI != null) curr.prevGUI!!.open()
        else curr.p.closeInventory()
    }
    fun getNext(p: Player) = ItemUtils.createItem(Material.ARROW, 1, getTitle(p, TranslatedString("menu.generic.next.title", arrayOf())))

    fun getTitle(p: Player, ts: TranslatedString): Component = TextUtils.legacyToMiniMessage(TextUtils.translate(Scoville.plugin, ts.id, p.locale(), *ts.args)).fromMiniMessage()
    fun getTitle(s: String): Component = TextUtils.legacyToMiniMessage(s).fromMiniMessage()
    fun getDesc(p: Player, ts: TranslatedString): Array<Component> = TextUtils.translateList(Scoville.plugin, ts.id, p.locale(), *ts.args).map { TextUtils.legacyToMiniMessage(it).fromMiniMessage() }.toTypedArray()

}