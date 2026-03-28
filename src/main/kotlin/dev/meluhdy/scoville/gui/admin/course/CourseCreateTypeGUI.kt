package dev.meluhdy.scoville.gui.admin.course

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scoville.gui.admin.course.types.OneJumpCourseCreateGUI
import dev.meluhdy.scoville.gui.admin.course.types.RankupCourseCreateGUI
import dev.meluhdy.scoville.gui.admin.course.types.UserCourseCreateGUI
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class CourseCreateTypeGUI(p: Player, pg: MelodiaGUI?) : MelodiaGUI(Scoville.plugin, p, pg), IScovilleGUI {

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.courses.type.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        MelodiaGUIItem(20, ItemUtils.createSkull(
            p.uniqueId, 1,
            getTitle(p, TranslatedString("menu.courses.type.user.title", arrayOf()))
        )) {
            p.closeInventory()
            TextUtils.prompt(getTitle(p, TranslatedString("menu.admin.courses.create.prompt", arrayOf())) as TextComponent, p) {
                UserCourseCreateGUI(it.content(), p, this).open()
            }
        },
        MelodiaGUIItem(22, ItemUtils.createItem(
            Material.NAME_TAG, 1,
            getTitle(p, TranslatedString("menu.courses.type.rankup.title", arrayOf()))
        )) {
            RankupCourseCreateGUI(p, this).open()
        },
        MelodiaGUIItem(24, ItemUtils.createItem(
            Material.OAK_SAPLING, 1,
            getTitle(p, TranslatedString("menu.courses.type.oj.title", arrayOf()))
        )) {
            p.closeInventory()
            TextUtils.prompt(getTitle(p, TranslatedString("menu.admin.courses.create.prompt", arrayOf())) as TextComponent, p) {
                OneJumpCourseCreateGUI(it.content(), p, this).open()
            }
        },
        getBack(36, this)
    )

    override fun extraItems() {
        this.createBorder(this)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}