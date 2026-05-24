package dev.meluhdy.scoville.misc

import com.jeff_media.customblockdata.CustomBlockData
import com.jeff_media.morepersistentdatatypes.DataType
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object BlockDataUtils {

    fun Block.removeData(plugin: JavaPlugin, key: NamespacedKey) {
        val data = CustomBlockData(this, plugin)
        data.remove(key)
    }

    fun Block.getDataLocation(plugin: JavaPlugin, key: NamespacedKey): Location? {
        val data = CustomBlockData(this, plugin)
        return data.get(key, DataType.LOCATION)
    }

    fun Block.setDataLocation(plugin: JavaPlugin, key: NamespacedKey, location: Location) {
        val data = CustomBlockData(this, plugin)
        data.set(key, DataType.LOCATION, location)
    }

    fun Block.getDataUUID(plugin: JavaPlugin, key: NamespacedKey): UUID? {
        val data = CustomBlockData(this, plugin)
        return data.get(key, DataType.UUID)
    }

    fun Block.setDataUUID(plugin: JavaPlugin, key: NamespacedKey, uuid: UUID) {
        val data = CustomBlockData(this, plugin)
        data.set(key, DataType.UUID, uuid)
    }

}