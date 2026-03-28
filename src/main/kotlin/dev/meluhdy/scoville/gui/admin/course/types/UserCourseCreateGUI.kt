package dev.meluhdy.scoville.gui.admin.course.types

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.core.course.courses.UserCourse
import dev.meluhdy.scoville.gui.admin.course.CourseCreateGUI
import dev.meluhdy.scoville.serialization.course.UserCourseSerializer
import org.bukkit.Material
import org.bukkit.entity.Player

class UserCourseCreateGUI(courseName: String, p: Player, pg: MelodiaGUI?) : CourseCreateGUI<UserCourseSerializer.UserCourseBuilder>(courseName, p, pg) {

    override var currBuilder: UserCourseSerializer.UserCourseBuilder = UserCourseSerializer.UserCourseBuilder()

    fun difficultyToItem(difficulty: UserCourse.Difficulty): Material {
        return when (difficulty) {
            UserCourse.Difficulty.SWEET -> Material.GREEN_TERRACOTTA
            UserCourse.Difficulty.TANGY -> Material.GREEN_CONCRETE
            UserCourse.Difficulty.SAVORY -> Material.GREEN_WOOL
            UserCourse.Difficulty.ZESTY -> Material.GREEN_CONCRETE_POWDER
            UserCourse.Difficulty.SPICY -> Material.LIME_TERRACOTTA
            UserCourse.Difficulty.HOT -> Material.ORANGE_CONCRETE_POWDER
            UserCourse.Difficulty.SIZZLING -> Material.ORANGE_CONCRETE
            UserCourse.Difficulty.FIERY -> Material.RED_CONCRETE_POWDER
            UserCourse.Difficulty.SCORCHING -> Material.RED_CONCRETE
            UserCourse.Difficulty.BLAZING -> Material.NETHER_WART_BLOCK
            else -> Material.STONE_BUTTON
        }
    }

    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() {
            val currDiff = this.currBuilder.difficulty

            val difficultyItems = ArrayList<MelodiaGUIItem>()
            if (currDiff > UserCourse.Difficulty.SWEET) {
                val prevDiff = UserCourse.Difficulty.entries[currDiff.ordinal - 1]
                difficultyItems.add(MelodiaGUIItem(28, ItemUtils.createItem(
                    Material.ARROW, 1,
                    this.getTitle(p, TranslatedString("menu.admin.courses.create.diff.title", arrayOf(prevDiff.color, prevDiff.ordinal)))
                )) {
                    this.currBuilder.difficulty = prevDiff
                    this.initializeItems()
                    p.updateInventory()
                })
            }
            difficultyItems.add(MelodiaGUIItem(31, ItemUtils.createItem(
                difficultyToItem(currDiff), 1,
                getTitle(p, TranslatedString("menu.admin.courses.create.diff.title", arrayOf(currDiff.color, currDiff.ordinal)))
            )) {})
            if (currDiff.ordinal < UserCourse.Difficulty.entries.size - 1) {
                val nextDiff = UserCourse.Difficulty.entries[currDiff.ordinal + 1]
                difficultyItems.add(MelodiaGUIItem(34, ItemUtils.createItem(
                    Material.ARROW, 1,
                    this.getTitle(p, TranslatedString("menu.admin.courses.create.diff.title", arrayOf(nextDiff.color, nextDiff.ordinal)))
                )) {
                    this.currBuilder.difficulty = nextDiff
                    this.initializeItems()
                    p.updateInventory()
                })
            }
            return (super.melodiaItems + difficultyItems) as ArrayList<MelodiaGUIItem>
        }

}