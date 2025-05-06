package dev.meluhdy.scoville.achievement

import dev.meluhdy.melodia.manager.MelodiaItem
import dev.meluhdy.scoville.Scoville.Companion.plugin
import dev.meluhdy.scoville.event.event.GrantAchievementEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import java.lang.reflect.ParameterizedType

// UUID is not used here, use achievementId instead
abstract class Achievement<T: PlayerEvent>(): MelodiaItem(), Listener {

    init {
        @Suppress("UNCHECKED_CAST")
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        @Suppress("UNCHECKED_CAST")
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.NORMAL, { _, event -> triggerImpl(event as T) },
            plugin)
    }

    enum class AchievementDifficulty(val color: Char) {
        EASY('a'),
        MEDIUM('6'),
        HARD('c'),
        SPECIAL('f')
    }

    abstract val achievementId: String

    abstract val nameId: String

    abstract val descId: String

    abstract val diff: AchievementDifficulty

    abstract val baseStack: ItemStack

    fun triggerImpl(event: T) {
        // TODO: Ensure player doesn't already have achievement
        if (check(event)) {
            Bukkit.getPluginManager().callEvent(GrantAchievementEvent(event.player, this))
            onAchievementGet(event.player)
        }
    }

    abstract fun check(event: T): Boolean

    abstract fun onAchievementGet(p: Player)

}