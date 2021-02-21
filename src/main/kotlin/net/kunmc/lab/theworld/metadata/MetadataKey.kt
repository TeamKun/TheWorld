package net.kunmc.lab.theworld.metadata

import org.bukkit.entity.Entity
import org.bukkit.util.Vector

sealed class MetadataKey<T>(val value: String) {
    object IsPlayerInteract : MetadataKey<Boolean>("IsPlayerInteract")
    object IsStopped : MetadataKey<Boolean>("IsStopped")
    object Velocity : MetadataKey<Vector>("Velocity")
    object Damage : MetadataKey<Double>("Damage")
    object Ticks : MetadataKey<Int>("Ticks")

    object IsCamera : MetadataKey<Boolean>("IsCamera")
    object Camera : MetadataKey<Entity>("Camera")
}