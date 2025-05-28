package dev.meluhdy.scoville.gui.admin.course

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scoville.serialization.course.AbstractCourseSerializer
import dev.meluhdy.scoville.serialization.course.UserCourseSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class CourseCreateGUI(courseName: String, p: Player, pg: MelodiaGUI?): MelodiaGUI(Scoville.plugin, p, pg), IScovilleGUI {

    var currBuilder: AbstractCourseSerializer.AbstractCourseBuilder<*> = UserCourseSerializer.UserCourseBuilder()

    init {
        currBuilder.name = courseName
    }

    override val rows: Int = 6
    override val title: TextComponent = getTitle(p, TranslatedString("menu.admin.courses.create.title", arrayOf(currBuilder.name ?: ""))) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() {
            return arrayListOf(
                MelodiaGUIItem(12, ItemUtils.createItem(Material.STONE_BUTTON, 1,
                    getTitle(p, TranslatedString("menu.admin.courses.create.colorname.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.admin.courses.create.colorname.desc", arrayOf(currBuilder.coloredName ?: "")))
                )) {
                    TextUtils.prompt(getTitle(p, TranslatedString("menu.admin.courses.create.colorname.prompt", arrayOf())) as TextComponent, p) {
                        this.currBuilder.coloredName = it.content()
                        this.open()
                    }
                },
                MelodiaGUIItem(13, ItemUtils.modifyItem(currBuilder.baseStack ?: ItemStack(Material.STONE_BUTTON),
                    getTitle(p, TranslatedString("menu.admin.courses.create.guiitem.title", arrayOf())),
                    *getDesc(p, TranslatedString("menu.admin.courses.create.guiitem.desc", arrayOf()))
                )) {
                    val item = it.cursor
                    if (item.type == Material.AIR) return@MelodiaGUIItem
                    this.currBuilder.baseStack = ItemStack(item.type, item.amount)
                    p.setItemOnCursor(ItemStack(Material.AIR))

                    p.updateInventory()
                }
            )
        }

    override fun extraItems() {
        createBorder(this)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}