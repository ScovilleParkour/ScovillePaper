package dev.meluhdy.scoville.achievement

import dev.meluhdy.melodia.manager.MelodiaManager
import dev.meluhdy.scoville.achievement.easy.TestAchievement

object AchievementManager : MelodiaManager<Achievement<*>>() {

    init {
        add(TestAchievement)
    }

}