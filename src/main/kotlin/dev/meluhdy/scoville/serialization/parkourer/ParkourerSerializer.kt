package dev.meluhdy.scoville.serialization.parkourer

import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.melodia.misc.serialization.UUIDSerializer
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.parkourer.Parkourer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import java.util.UUID

object ParkourerSerializer: MelodiaSerializer<Parkourer>() {

    class ParkourerBuilder: Builder<Parkourer>() {

        var currentlyPlaying: UUID? = null
        val courseCompletions: HashMap<UUID, Int> = HashMap()

        override fun build(): Parkourer {
            val out = Parkourer(uuid)
            out.currentlyPlaying = currentlyPlaying
            return out
        }

    }

    override val builder: Builder<Parkourer> = ParkourerBuilder()

    override val steps: Array<SerializerElement<*, Parkourer>> = arrayOf(
        SerializerElement("currentlyPlaying", String.serializer().nullable, { it.currentlyPlaying?.toString() }, { uuid, builder -> (builder as ParkourerBuilder).currentlyPlaying = uuid?.let { UUID.fromString(it) } }),
        SerializerElement("courseCompletions", MapSerializer(UUIDSerializer(), Int.serializer()), { it.getAllCourseCompletions().mapKeys { key -> key.key.uuid } }, { map, builder -> (builder as ParkourerBuilder).courseCompletions.putAll(map) })
    )

}