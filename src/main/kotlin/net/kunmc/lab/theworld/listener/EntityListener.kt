package net.kunmc.lab.theworld.listener

import net.kunmc.lab.theworld.TheWorld
import net.kunmc.lab.theworld.ext.getMeta
import net.kunmc.lab.theworld.ext.setMeta
import net.kunmc.lab.theworld.metadata.MetadataKey
import net.kunmc.lab.theworld.model.Stop
import org.bukkit.EntityEffect
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.util.Vector

class EntityListener(private val plugin: TheWorld) : Listener {
    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity
        if (plugin.isStopped) {
            Stop(plugin).stopEntity(entity)
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        val damager = event.damager
        if (plugin.isStopped && entity.getMeta(MetadataKey.IsStopped, false)) {
            val velocity = entity.getMeta(MetadataKey.Velocity, Vector(0.0, 0.0, 0.0))
            velocity.add(entity.location.subtract(damager.location).toVector().normalize().multiply(0.2))
            event.isCancelled = true
            entity.setMeta(plugin, MetadataKey.Velocity, velocity)
            entity.setMeta(plugin, MetadataKey.Damage, entity.getMeta(MetadataKey.Damage, 0.0) + event.damage)
            entity.playEffect(EntityEffect.HURT)
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (plugin.isStopped && entity.getMeta(MetadataKey.IsStopped, false)) {
            event.isCancelled = true
        }
    }
}