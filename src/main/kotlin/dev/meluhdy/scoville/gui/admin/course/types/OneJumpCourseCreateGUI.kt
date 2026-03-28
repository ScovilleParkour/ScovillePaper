package dev.meluhdy.scoville.gui.admin.course.types

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.core.course.courses.OneJumpCourse
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scoville.gui.admin.course.CourseCreateGUI
import dev.meluhdy.scoville.serialization.course.OneJumpCourseSerializer
import org.bukkit.Material
import org.bukkit.entity.Player

class OneJumpCourseCreateGUI(courseName: String, p: Player, pg: MelodiaGUI?) : CourseCreateGUI<OneJumpCourseSerializer.OneJumpCourseBuilder>(courseName, p, pg) {

    override var currBuilder: OneJumpCourseSerializer.OneJumpCourseBuilder = OneJumpCourseSerializer.OneJumpCourseBuilder()

    fun difficultyToItem(difficulty: OneJumpCourse.OJDifficulty): Material {
        return when (difficulty) {
            OneJumpCourse.OJDifficulty.D1 -> Material.LIGHT_BLUE_TERRACOTTA
            OneJumpCourse.OJDifficulty.D2 -> Material.LIME_TERRACOTTA
            OneJumpCourse.OJDifficulty.D3 -> Material.YELLOW_TERRACOTTA
            OneJumpCourse.OJDifficulty.D4 -> Material.ORANGE_TERRACOTTA
            OneJumpCourse.OJDifficulty.D5 -> Material.MAGENTA_TERRACOTTA
            OneJumpCourse.OJDifficulty.D6 -> Material.PINK_TERRACOTTA
            OneJumpCourse.OJDifficulty.D7 -> Material.PURPLE_TERRACOTTA
            OneJumpCourse.OJDifficulty.D8 -> Material.BLUE_TERRACOTTA
            OneJumpCourse.OJDifficulty.D9 -> Material.CYAN_TERRACOTTA
            OneJumpCourse.OJDifficulty.D10 -> Material.GRAY_TERRACOTTA
            OneJumpCourse.OJDifficulty.D11 -> Material.WHITE_TERRACOTTA
            OneJumpCourse.OJDifficulty.UNKNOWN -> Material.STONE_BUTTON
        }
    }

    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() {
            val currDiff = this.currBuilder.difficulty

            val difficultyItems = ArrayList<MelodiaGUIItem>()
            if (currDiff > OneJumpCourse.OJDifficulty.D1) {
                val prevDiff = OneJumpCourse.OJDifficulty.entries[currDiff.ordinal - 1]
                difficultyItems.add(MelodiaGUIItem(19, ItemUtils.createItem(
                    Material.ARROW, 1,
                    this.getTitle(p, TranslatedString("menu.admin.courses.create.diff.title", arrayOf(prevDiff.color, prevDiff.ordinal)))
                )) {
                    this.currBuilder.difficulty = prevDiff
                    this.initializeItems()
                    p.updateInventory()
                })
            }
            difficultyItems.add(MelodiaGUIItem(22, ItemUtils.createItem(
                difficultyToItem(currDiff), 1,
                getTitle(p, TranslatedString("menu.admin.courses.create.diff.title", arrayOf(currDiff.color, currDiff.ordinal)))
            )) {})
            if (currDiff.ordinal < OneJumpCourse.OJDifficulty.entries.size + 1) {
                val nextDiff = OneJumpCourse.OJDifficulty.entries[currDiff.ordinal + 1]
                difficultyItems.add(MelodiaGUIItem(25, ItemUtils.createItem(
                    Material.ARROW, 1,
                    this.getTitle(p, TranslatedString("menu.admin.courses.create.diff.title", arrayOf(nextDiff.color, nextDiff.ordinal)))
                )) {
                    this.currBuilder.difficulty = nextDiff
                    this.initializeItems()
                    p.updateInventory()
                })
            }

            val jumpItems = ArrayList<MelodiaGUIItem>()
            if (this.currBuilder.jumps > 0) {
                jumpItems.add(MelodiaGUIItem(28, ItemUtils.createItem(
                    Material.ARROW, 1,
                    this.getTitle(p, TranslatedString("menu.admin.courses.create.oj.jumps.title", arrayOf(this.currBuilder.jumps - 1)))
                )) {
                    this.currBuilder.jumps -= 1
                    this.initializeItems()
                    p.updateInventory()
                })
            }
            jumpItems.add(MelodiaGUIItem(31, ItemUtils.createItem(
                Material.ARROW, 1,
                this.getTitle(p, TranslatedString("menu.admin.courses.create.oj.jumps.title", arrayOf(this.currBuilder.jumps)))
            )) {})
            jumpItems.add(MelodiaGUIItem(34, ItemUtils.createItem(
                Material.ARROW, 1,
                this.getTitle(p, TranslatedString("menu.admin.courses.create.oj.jumps.title", arrayOf(this.currBuilder.jumps + 1)))
            )) {
                this.currBuilder.jumps += 1
                this.initializeItems()
                p.updateInventory()
            })

            return (super.melodiaItems + difficultyItems) as ArrayList<MelodiaGUIItem>
        }

    override fun extraItems() {
        this.createBorder(this, IScovilleGUI.ojPanes)
    }

}