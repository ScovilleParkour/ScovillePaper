package dev.meluhdy.scoville.serialization.parkourer

import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.scoville.achievement.Achievement
import dev.meluhdy.scoville.achievement.AchievementManager
import dev.meluhdy.scoville.core.parkourer.Parkourer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import java.util.UUID

object ParkourerSerializer: MelodiaSerializer<Parkourer>() {

    class ParkourerBuilder: Builder<Parkourer>() {

        var currentlyPlaying: UUID? = null
        val achievements: MutableList<String> = mutableListOf()

        override fun build(): Parkourer {
            val out = Parkourer(uuid)
            out.currentlyPlaying = currentlyPlaying
            achievements.forEach { out.grantAchievement(it) }
            return out
        }

    }

    override val builder: Builder<Parkourer> = ParkourerBuilder()

    override val steps: Array<SerializerElement<*, Parkourer>> = arrayOf(
        SerializerElement<String?, Parkourer>("currentlyPlaying", String.serializer().nullable, { it.currentlyPlaying?.toString() }, { uuid, builder -> (builder as ParkourerBuilder).currentlyPlaying = uuid?.let { UUID.fromString(it) } }),
        SerializerElement<List<String>, Parkourer>("achievements", ListSerializer(String.serializer()), { it.getAchievementList().map { achievement -> achievement.achievementId } }, { list, builder -> (builder as ParkourerBuilder).achievements.addAll(list) })
    )

}