package dev.meluhdy.scoville.gui.admin.course

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
import dev.meluhdy.scoville.gui.IScovilleGUI
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class CourseAdminGUI(p: Player, pg: MelodiaGUI?): MelodiaPaginationGUI<AbstractCourse>(Scoville.Companion.plugin, p, pg),
    IScovilleGUI {

    override val itemRows: Int = 3
    override val prevItem: ItemStack = getBack(36, this).item
    override val nextItem: ItemStack = getNext(p)
    override val objects: ArrayList<AbstractCourse> = CourseManager.getAll()

    override fun toItem(
        pos: Int,
        obj: AbstractCourse
    ): MelodiaGUIItem = MelodiaGUIItem(
        pos, ItemUtils.modifyItem(
            obj.baseStack ?: ItemStack(Material.STONE_BUTTON), getTitle(obj.coloredName ?: ""),
            getTitle("&8${obj.authors.joinToString(", ") { uuid -> UUIDManager.getName(uuid) }}")
        )
    ) {
        it.whoClicked.sendMessage("Fuck ${obj.name}")
    }

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.courses.title", arrayOf())) as TextComponent

    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        MelodiaGUIItem(
            40, ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/5ff31431d64587ff6ef98c0675810681f8c13bf96f51d9cb07ed7852b2ffd1",
                1,
                getTitle(p, TranslatedString("menu.admin.courses.new", arrayOf())) as TextComponent
                )
        ) {
            CourseCreateTypeGUI(p, this).open()
        }
    )

    override fun extraItems() {
        createRow(this, 3)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}
}