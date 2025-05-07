package dev.meluhdy.scoville.gui

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.utils.ItemUtils
import org.bukkit.Material
import kotlin.math.ceil

private val coloredPanes = arrayOf(
    Material.RED_STAINED_GLASS_PANE,
    Material.ORANGE_STAINED_GLASS_PANE,
    Material.YELLOW_STAINED_GLASS_PANE,
    Material.LIME_STAINED_GLASS_PANE,
    Material.GREEN_STAINED_GLASS_PANE
)

interface IScovilleGUI {

    fun createRow(inv: MelodiaGUI, row: Int) {
        if (row > inv.rows) throw IndexOutOfBoundsException("Tried to create row outside of inventory!")
        val start = 9 * row
        for (i in 0..<5) {
            inv.inv.setItem(start + i, ItemUtils.createItem(coloredPanes[i], 1, "&r"))
            inv.inv.setItem(start + 8 - i, ItemUtils.createItem(coloredPanes[i], 1, "&r"))
        }
    }

    fun createBorder(inv: MelodiaGUI) {
        val lastRow = inv.rows - 1
        createRow(inv, 0)
        createRow(inv, lastRow)
        for (i in 1..<ceil(inv.inv.size / 18.0).toInt()) {
            inv.inv.setItem(9 * i, ItemUtils.createItem(coloredPanes[i], 1, "&r"))
            inv.inv.setItem(9 * (lastRow - i), ItemUtils.createItem(coloredPanes[i], 1, "&r"))
            inv.inv.setItem(9 * i + 8, ItemUtils.createItem(coloredPanes[i], 1, "&r"))
            inv.inv.setItem(9 * (lastRow - i) + 8, ItemUtils.createItem(coloredPanes[i], 1, "&r"))
        }
    }

}