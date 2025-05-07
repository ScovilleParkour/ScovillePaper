package dev.meluhdy.scoville.gui

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.Scoville
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class MainMenuGUI(player: Player): MelodiaGUI(Scoville.plugin, player), IScovilleGUI {

    override val rows: Int = 6
    override val title = TextUtils.legacyToMiniMessage(TextUtils.translate(plugin, "menu.main.title", p.locale())).fromMiniMessage() as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        MelodiaGUIItem(10, ItemUtils.createItem(
            Material.OAK_LEAVES, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.greenhouse.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.greenhouse.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Greenhouse")
        },
        MelodiaGUIItem(13, ItemUtils.createItem(
            Material.ENDER_EYE, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.courses.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.courses.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Courses")
        },
        MelodiaGUIItem(16, ItemUtils.createItem(
            Material.FEATHER, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.progjumps.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.progjumps.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("ProgJumps")
        },
        MelodiaGUIItem(19, ItemUtils.createItem(
            Material.NAME_TAG, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.cosmetics.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.cosmetics.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Cosmetics")
        },
        MelodiaGUIItem(22, ItemUtils.createSkull(
            "https://textures.minecraft.net/texture/d55fc2c1bae8e08d3e426c17c455d2ff9342286dffa3c7c23f4bd365e0c3fe", 1,
            TextUtils.translate(Scoville.plugin, "menu.main.onejump.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.onejump.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("OneJump")
        },
        MelodiaGUIItem(25, ItemUtils.createItem(
            Material.DIAMOND_PICKAXE, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.creative.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.creative.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Creative")
        },
        MelodiaGUIItem(45, ItemUtils.createItem(
            Material.BEACON, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.spawn.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.spawn.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Spawn")
        },
        MelodiaGUIItem(48, ItemUtils.createItem(
            Material.MUSIC_DISC_CHIRP, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.radio.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.radio.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Radio")
        },
        MelodiaGUIItem(50, ItemUtils.createItem(
            Material.EMERALD, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.achievements.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.achievements.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Achievements")
        },
        MelodiaGUIItem(53, ItemUtils.createItem(
            Material.EXPERIENCE_BOTTLE, 1,
            TextUtils.translate(Scoville.plugin, "menu.main.levels.title", player.locale()),
            *TextUtils.translateList(Scoville.plugin, "menu.main.levels.desc", player.locale()).toTypedArray()
        )) {
            it.whoClicked.sendMessage("Level Rewards")
        }
    )

    override fun extraItems() {
        createRow(this, 4)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}