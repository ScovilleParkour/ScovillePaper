package dev.meluhdy.scoville.gui

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.Scoville
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class ConfirmationGUI(player: Player, prevGUI: MelodiaGUI?, function: () -> Unit): MelodiaGUI(Scoville.plugin, player, prevGUI), IScovilleGUI {

    override val rows: Int = 5
    override val title: TextComponent = getTitle(player, TranslatedString("menu.confirm.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        MelodiaGUIItem(21, ItemUtils.createItem(Material.LIME_DYE, 1,
            getTitle(player, TranslatedString("menu.confirm.yes.title", arrayOf())) as TextComponent
        )) {
            function()
        },
        MelodiaGUIItem(23, ItemUtils.createItem(Material.RED_DYE, 1,
            getTitle(player, TranslatedString("menu.confirm.no.title", arrayOf())) as TextComponent
        )) {
            if (prevGUI != null) prevGUI.open() else p.closeInventory()
        }
    )

    override fun extraItems() {
        TODO("Not yet implemented")
    }

    override fun onInventoryClick(e: InventoryClickEvent) {
        TODO("Not yet implemented")
    }
}