package net.kunmc.lab.theworld.range

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import java.util.*

class RectRange(private val x: Double, private val y: Double, private val z: Double) : Range() {
    override fun getEntities(location: Location): List<Entity> {
        return location.world?.getNearbyEntities(location, x, y, z)?.toList() ?: emptyList()
    }

    override fun getBlocks(location: Location): List<Block> {
        val result = mutableListOf<Block>()
        ((x.toInt() * -1)..(x.toInt())).forEach { i ->
            ((y.toInt() * -1)..(y.toInt())).forEach { j ->
                ((z.toInt() * -1)..(z.toInt())).forEach { k ->
                    val block = location.world?.getBlockAt(x.toInt() + i, y.toInt() + j, z.toInt() + k)
                    block?.let {
                        result.add(it)
                    }
                }
            }
        }
        return result
    }

    override fun randomLocation(baseLocation: Location): Location {
        val i = (Random().nextDouble() * 2 - 1) * x
        val j = (Random().nextDouble() * 2 - 1) * y
        val k = (Random().nextDouble() * 2 - 1) * z
        return baseLocation.clone().add(i, j, k)
    }
}