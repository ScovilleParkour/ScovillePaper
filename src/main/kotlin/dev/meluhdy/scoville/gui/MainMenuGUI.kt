package dev.meluhdy.scoville.gui

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.gui.admin.AdminPanelGUI
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class MainMenuGUI(player: Player): MelodiaGUI(Scoville.plugin, player), IScovilleGUI {

    override val rows: Int = 6
    override val title: TextComponent = getTitle(player, TranslatedString("menu.main.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() {
            val start = arrayListOf(
                MelodiaGUIItem(10, ItemUtils.createItem(
                    Material.OAK_LEAVES, 1,
                    getTitle(p, TranslatedString("menu.main.greenhouse.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.greenhouse.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("Greenhouse")
                },
                MelodiaGUIItem(13, ItemUtils.createItem(
                    Material.ENDER_EYE, 1,
                    getTitle(p, TranslatedString("menu.main.courses.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.courses.desc", arrayOf()))
                )) {
                    CourseGUI(p, this).open()
                },
                MelodiaGUIItem(16, ItemUtils.createItem(
                    Material.FEATHER, 1,
                    getTitle(p, TranslatedString("menu.main.progjumps.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.progjumps.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("ProgJumps")
                },
                MelodiaGUIItem(19, ItemUtils.createItem(
                    Material.NAME_TAG, 1,
                    getTitle(p, TranslatedString("menu.main.cosmetics.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.cosmetics.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("Cosmetics")
                },
                MelodiaGUIItem(22, ItemUtils.createSkull(
                    "https://textures.minecraft.net/texture/d55fc2c1bae8e08d3e426c17c455d2ff9342286dffa3c7c23f4bd365e0c3fe", 1,
                    getTitle(p, TranslatedString("menu.main.onejump.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.onejump.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("OneJump")
                },
                MelodiaGUIItem(25, ItemUtils.createItem(
                    Material.DIAMOND_PICKAXE, 1,
                    getTitle(p, TranslatedString("menu.main.creative.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.creative.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("Creative")
                },
                MelodiaGUIItem(45, ItemUtils.createItem(
                    Material.BEACON, 1,
                    getTitle(p, TranslatedString("menu.main.spawn.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.spawn.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("Spawn")
                },
                MelodiaGUIItem(48, ItemUtils.createItem(
                    Material.MUSIC_DISC_CHIRP, 1,
                    getTitle(p, TranslatedString("menu.main.radio.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.radio.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("Radio")
                },
                MelodiaGUIItem(50, ItemUtils.createItem(
                    Material.EMERALD, 1,
                    getTitle(p, TranslatedString("menu.main.achievements.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.achievements.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("Achievements")
                },
                MelodiaGUIItem(53, ItemUtils.createItem(
                    Material.EXPERIENCE_BOTTLE, 1,
                    getTitle(p, TranslatedString("menu.main.levels.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.main.levels.desc", arrayOf()))
                )) {
                    it.whoClicked.sendMessage("Level Rewards")
                }
            )

            if (p.hasPermission("scoville.course.edit")) start.add(MelodiaGUIItem(49, ItemUtils.createItem(
                Material.COMMAND_BLOCK, 1,
                getTitle(p, TranslatedString("menu.main.admin.title", arrayOf())),
                *getDesc(p, TranslatedString("menu.main.admin.desc", arrayOf()))
            )) {
                AdminPanelGUI(p, this).open()
            })

            return start
        }

    override fun extraItems() {
        createRow(this, 4)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}