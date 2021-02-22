package net.kunmc.lab.theworld.model

import net.kunmc.lab.theworld.TheWorld
import net.kunmc.lab.theworld.effect.ResetCameraEffect
import net.kunmc.lab.theworld.effect.StopCameraEffect
import net.kunmc.lab.theworld.effect.TimeStartEffect
import net.kunmc.lab.theworld.effect.TimeStopEffect
import net.kunmc.lab.theworld.ext.getMeta
import net.kunmc.lab.theworld.ext.removeMeta
import net.kunmc.lab.theworld.ext.runTaskLater
import net.kunmc.lab.theworld.ext.setMeta
import net.kunmc.lab.theworld.metadata.MetadataKey
import net.kunmc.lab.theworld.range.RectRange
import net.kunmc.lab.theworld.rx.Observable
import org.bukkit.entity.Creeper
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.util.Vector

class Stop(
        private val plugin: TheWorld
) {
    fun stopEntities(entities: List<Entity>) {
        entities.forEach {
            stopEntity(it)
        }
    }

    fun stopEntity(entity: Entity) {
        plugin.runTaskLater(1) {
            if (entity is Player) {
                StopCameraEffect(plugin).execute(entity)
            } else {
                entity.setGravity(false)
            }

            val velocity = entity.velocity
            entity.setMeta(plugin, MetadataKey.Velocity, velocity)
            entity.velocity = Vector(0.0, 0.0, 0.0)
            entity.location.direction = velocity
            entity.setMeta(plugin, MetadataKey.IsStopped, true)
            if (entity is LivingEntity) {
                entity.setAI(false)
            }
            if (entity is Creeper) {
                entity.setMeta(plugin, MetadataKey.Ticks, entity.maxFuseTicks)
                entity.maxFuseTicks = 100000
            }
            if (entity is TNTPrimed) {
                entity.setMeta(plugin, MetadataKey.Ticks, entity.fuseTicks)
                entity.fuseTicks = 100000
            }
        }
    }

    fun startEntities(entities: List<Entity>) {
        entities.forEach {
            startEntity(it)
        }
    }

    fun startEntity(entity: Entity) {
        plugin.runTaskLater(1) {
            val velocity = entity.getMeta(MetadataKey.Velocity, Vector(0.0, 0.0, 0.0))
            entity.velocity = velocity
            entity.removeMeta(plugin, MetadataKey.Velocity)
            entity.removeMeta(plugin, MetadataKey.IsStopped)
            if (entity is Player) {
                ResetCameraEffect(plugin).execute(entity)
            } else {
                entity.setGravity(true)
            }
            if (entity is LivingEntity) {
                entity.setAI(true)
                entity.damage(entity.getMeta(MetadataKey.Damage, 0.0))
                entity.removeMeta(plugin, MetadataKey.Damage)
            }
            if (entity is Creeper) {
                val ticks = entity.getMeta(MetadataKey.Ticks, 0)
                entity.maxFuseTicks = ticks
            }
            if (entity is TNTPrimed) {
                val ticks = entity.getMeta(MetadataKey.Ticks, 0)
                entity.fuseTicks = ticks
            }
        }
    }

    fun execute(player: Player, seconds: Int) {
        if (plugin.isStopped) {
            startEntity(player)
            player.sendActionBar("止まった時間の中で動けるようになった")
            return
        }
        val entities = player.world.entities
        val players = entities.filterIsInstance<Player>()
        TimeStopEffect(plugin).execute(players)
        players.forEach {
            plugin.bossBar.addPlayer(it)
        }

        plugin.isStopped = true

        stopEntities(entities.filter { it != player })

        Observable.interval(20)
                .take(seconds.toLong())
                .doOnNext {
                    plugin.bossBar.progress = (seconds - it).toDouble() / seconds.toDouble()
                }
                .doOnComplete {
                    TimeStartEffect(plugin).execute(players)
                    plugin.isStopped = false
                    plugin.bossBar.removeAll()
                    startEntities(player.world.entities.filter { it.getMeta(MetadataKey.IsStopped, false) }.filter { !it.getMeta(MetadataKey.IsCamera, false) })
                }
                .subscribe(plugin)
    }
}