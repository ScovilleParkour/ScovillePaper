package dev.meluhdy.scoville.gui.course

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.gui.MelodiaPaginationGUI
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.uuid.UUIDManager
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.course.courses.OneJumpCourse
import dev.meluhdy.scoville.core.course.courses.UserCourse
import dev.meluhdy.scoville.gui.IScovilleGUI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.apache.commons.lang3.StringUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.text.DecimalFormat
import kotlin.math.roundToInt

class CourseListGUI(val courseType: AbstractCourse.CourseType, p: Player, pg: MelodiaGUI?): MelodiaPaginationGUI<AbstractCourse>(Scoville.plugin, p, pg),
    IScovilleGUI {

    override val itemRows: Int = 3
    override val prevItem: ItemStack = getBack(36, this).item
    override val nextItem: ItemStack = ItemUtils.createItem(
        Material.ARROW, 1, getTitle(p,
            TranslatedString("menu.generic.next.title", arrayOf())
        ))
    override val objects: ArrayList<AbstractCourse> = CourseManager.getAll().filter { course -> course.courseType == courseType }.toCollection(ArrayList())

    fun getLore(obj: AbstractCourse): MutableList<Component> {
        val wlrBars = '▮'
        val rateBars = '✦'
        val wlrBarCount = 20
        val rateBarCount = 5
        val colorMap = hashMapOf(
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

        val lore: MutableList<Component> = when (obj) {
            is UserCourse -> mutableListOf(
                getTitle("&8${obj.authors.joinToString(", ") { uuid -> UUIDManager.getName(uuid) }}"),
                getTitle(p, TranslatedString("menu.courses.course.diff.${obj.difficulty.ordinal}", arrayOf()))
            )
            is OneJumpCourse -> mutableListOf(
                getTitle("&8${obj.authors.joinToString(", ") { uuid -> UUIDManager.getName(uuid) }}"),
                getTitle(p, TranslatedString("menu.courses.course.diff.oj", arrayOf(obj.difficulty.ordinal, obj.jumps)))
            )
            else -> mutableListOf()
        }
        lore.addAll(mutableListOf(
            Component.empty(),
            getTitle(p, TranslatedString("menu.courses.course.cr", arrayOf())),
            getTitle(
                "&8[&a${StringUtils.repeat(wlrBars, wlr)}&7${
                    StringUtils.repeat(
                        wlrBars,
                        wlrBarCount - wlr
                    )
                }&8] &7${DecimalFormat("0.00").format(.5f * 100.0f)}%"
            ),
            getTitle(
                "&7${
                    TextUtils.translate(
                        Scoville.plugin,
                        "menu.courses.course.ur",
                        p.locale(),
                        arrayOf<String>()
                    )
                }: &${colorMap[rate]}${StringUtils.repeat(rateBars, rate)}&7${
                    StringUtils.repeat(
                        rateBars,
                        rateBarCount - rate
                    )
                } - &${colorMap[rate]}${DecimalFormat("0.00").format(2.5f)}"
            ),
            Component.empty(),
            getTitle(
                "&8${
                    TextUtils.translate(
                        Scoville.plugin,
                        "menu.courses.course.tc",
                        p.locale(),
                        arrayOf<String>()
                    )
                }: &7${0}"
            )
        ))

        return lore
    }

    override fun toItem(
        pos: Int,
        obj: AbstractCourse
    ): MelodiaGUIItem {

        return MelodiaGUIItem(
            pos, ItemUtils.modifyItem(
                obj.baseStack ?: ItemStack(Material.STONE_BUTTON),
                getTitle(obj.coloredName ?: "Unknown Course"),
                *this.getLore(obj).toTypedArray()
            )
        ) {
            it.whoClicked.sendMessage("Join ${obj.name}")
        }
    }

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.courses.title", arrayOf())) as TextComponent

    override fun open() {
        val courses = CourseManager.getAll()
        Scoville.plugin.logger.debug("Length: ${courses.size}")
        courses.forEach { course -> Scoville.plugin.logger.debug("Course: ${course.name}") }
        Scoville.plugin.logger.debug("Filtered Length: ${objects.size}")
        courses.forEach { obj -> Scoville.plugin.logger.debug("Course: ${obj.name}") }
        super.open()
    }

    override fun extraItems() {
        createRow(this, 3)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}