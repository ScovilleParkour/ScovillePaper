package dev.meluhdy.scoville.serialization.plate

import dev.meluhdy.melodia.misc.serialization.LocationSerializer
import dev.meluhdy.scoville.core.plate.Plate
import dev.meluhdy.melodia.misc.serialization.MelodiaSerializer
import dev.meluhdy.melodia.misc.serialization.SerializerElement
import dev.meluhdy.melodia.misc.serialization.UUIDSerializer
import kotlinx.serialization.builtins.nullable
import org.bukkit.Location
import java.util.UUID

object PlateSerializer: MelodiaSerializer<Plate>() {

    class ParkourerBuilder: Builder<Plate>() {

        var course: UUID? = null
        var location: Location? = null

        override fun build(): Plate {
            val out = Plate(uuid)
            this.course?.let { out.setCourse(it) }
            this.location?.let { out.location = it }
            return out
        }

    }

    override val builder: Builder<Plate> = ParkourerBuilder()

    override val steps: Array<SerializerElement<*, Plate>> = arrayOf(
        SerializerElement("course", UUIDSerializer().nullable, { it.getCourse()?.uuid }, { value, obj -> (obj as ParkourerBuilder).course = value }),
        SerializerElement("location", LocationSerializer().nullable, { it.location }, { value, obj -> (obj as ParkourerBuilder).location = value })
    )

}