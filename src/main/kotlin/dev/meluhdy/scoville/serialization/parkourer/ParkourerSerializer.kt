package dev.meluhdy.scoville.serialization.parkourer

import dev.meluhdy.melodia.misc.serialization.LocationSerializer
import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.melodia.misc.serialization.UUIDSerializer
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.parkourer.Parkourer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import org.bukkit.Location
import java.util.UUID

object ParkourerSerializer: MelodiaSerializer<Parkourer>() {

    class ParkourerBuilder: Builder<Parkourer>() {

        var currentlyPlaying: UUID? = null
        val courseCompletions: HashMap<UUID, Int> = HashMap()
        val checkpoints: HashMap<UUID, Location> = HashMap()

        override fun build(): Parkourer {
            val out = Parkourer(uuid)
            out.currentlyPlaying = currentlyPlaying
            out.courseCompletionCount.putAll(courseCompletions)
            out.checkpoints.putAll(checkpoints)
            return out
        }

    }

    override val builder: Builder<Parkourer> = ParkourerBuilder()

    override val steps: Array<SerializerElement<*, Parkourer>> = arrayOf(
        SerializerElement("currentlyPlaying", String.serializer().nullable, { it.currentlyPlaying?.toString() }, { uuid, builder -> (builder as ParkourerBuilder).currentlyPlaying = uuid?.let { UUID.fromString(it) } }),
        SerializerElement("courseCompletions", MapSerializer(UUIDSerializer, Int.serializer()), { it.courseCompletionCount }, { map, builder -> (builder as ParkourerBuilder).courseCompletions.putAll(map) }),
        SerializerElement("checkpoints", MapSerializer(UUIDSerializer, LocationSerializer), { it.checkpoints }, { map, builder -> (builder as ParkourerBuilder).checkpoints.putAll(map) })
    )

}