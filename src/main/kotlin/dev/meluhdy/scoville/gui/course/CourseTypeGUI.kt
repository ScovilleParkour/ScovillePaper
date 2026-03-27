package dev.meluhdy.scoville.gui.course

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.gui.IScovilleGUI
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class CourseTypeGUI(p: Player, pg: MelodiaGUI?) : MelodiaGUI(Scoville.plugin, p, pg), IScovilleGUI {

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.courses.type.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        MelodiaGUIItem(20, ItemUtils.createSkull(
            p.uniqueId, 1,
            getTitle(p, TranslatedString("menu.courses.type.user.title", arrayOf()))
        )) {
            CourseGUI(AbstractCourse.CourseType.USER, p, this).open()
        },
        MelodiaGUIItem(22, ItemUtils.createItem(
            Material.NAME_TAG, 1,
            getTitle(p, TranslatedString("menu.courses.type.rankup.title", arrayOf()))
        )) {
            CourseGUI(AbstractCourse.CourseType.RANKUP, p, this).open()
        },
        MelodiaGUIItem(24, ItemUtils.createItem(
            Material.OAK_SAPLING, 1,
            getTitle(p, TranslatedString("menu.courses.type.oj.title", arrayOf()))
        )) {
            CourseGUI(AbstractCourse.CourseType.ONEJUMP, p, this).open()
        }
    )

    override fun extraItems() {
        this.createBorder(this)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}