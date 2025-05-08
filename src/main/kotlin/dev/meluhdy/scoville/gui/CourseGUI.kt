package dev.meluhdy.scoville.gui

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.gui.MelodiaPaginationGUI
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.melodia.utils.uuid.UUIDManager
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.course.courses.UserCourse
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.apache.commons.lang3.StringUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.text.DecimalFormat
import kotlin.math.roundToInt

class CourseGUI(p: Player, pg: MelodiaGUI?): MelodiaPaginationGUI<UserCourse>(Scoville.plugin, p, pg), IScovilleGUI {

    override val itemRows: Int = 3
    override val prevItem: ItemStack = getBack(36, this).item
    override val nextItem: ItemStack = ItemUtils.createItem(Material.ARROW, 1, getTitle(p, TranslatedString("menu.generic.next.title", arrayOf())))
    override val objects: ArrayList<UserCourse> = CourseManager.getAll().filter { course -> course.courseType == AbstractCourse.CourseType.USER }.map { course -> course as UserCourse }.toCollection(ArrayList())

    override fun toItem(
        pos: Int,
        obj: UserCourse
    ): MelodiaGUIItem {
        val wlrBars = '▮'
        val rateBars = '✦'
        val wlrBarCount = 20
        val rateBarCount = 5
        val colorMap = hashMapOf<Int, Char>(
            0 to '8',
            1 to '4',
            2 to 'c',
            3 to 'e',
            4 to 'a',
            5 to '2'
        )

        // TODO: Store Course WLR and Rate
        val wlr = (.5f * wlrBarCount).roundToInt()
        val rate = (2.5f).roundToInt()

        return MelodiaGUIItem(
            pos, ItemUtils.modifyItem(
                obj.baseStack, getTitle(obj.coloredName), *arrayOf<Component>(
                    TextUtils.legacyToMiniMessage("&8${obj.authors.joinToString(", ") { uuid -> UUIDManager.getName(uuid) }}")
                        .fromMiniMessage(),
                    getTitle(p, TranslatedString("menu.courses.course.diff.${obj.difficulty.ordinal}", arrayOf())),
                    Component.empty(),
                    getTitle(p, TranslatedString("menu.courses.course.cr", arrayOf())),
                    getTitle("&8[&a${StringUtils.repeat(wlrBars, wlr)}&7${StringUtils.repeat(wlrBars, wlrBarCount - wlr)}&8] &7${DecimalFormat("0.00").format(.5f * 100.0f)}%"),
                    getTitle("&7${TextUtils.translate(Scoville.plugin, "menu.courses.course.ur", p.locale(), arrayOf<String>())}: &${colorMap[rate]}${StringUtils.repeat(rateBars, rate)}&7${StringUtils.repeat(rateBars, rateBarCount - rate)} - &${colorMap[rate]}${DecimalFormat("0.00").format(2.5f)}"),
                    Component.empty(),
                    getTitle("&8${TextUtils.translate(Scoville.plugin, "menu.courses.course.tc", p.locale(), arrayOf<String>())}: &7${0}")
                )
            )
        ) {
            it.whoClicked.sendMessage("Join ${obj.name}")
        }
    }

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.courses.title", arrayOf())) as TextComponent

    override fun extraItems() {
        createRow(this, 3)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}


}