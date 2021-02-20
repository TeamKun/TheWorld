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
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
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
        }
    }

    fun execute(player: Player, seconds: Int) {
        if (plugin.isStopped) {
            startEntity(player)
            player.sendMessage("止まった時間の中で動けるようになった")
            return
        }
        val range = RectRange(50.0, 50.0, 50.0)
        val players = range.getEntities(player.location).filterIsInstance<Player>()
        TimeStopEffect(plugin).execute(players)
        plugin.isStopped = true

        stopEntities(player.world.entities.filter { it != player })

        Observable.interval(20)
                .take(seconds.toLong())
                .doOnNext {
                    player.sendMessage("${seconds - it}秒前")
                }
                .doOnComplete {
                    TimeStartEffect(plugin).execute(players)
                    plugin.isStopped = false
                    startEntities(player.world.entities.filter { it.getMeta(MetadataKey.IsStopped, false) }.filter { !it.getMeta(MetadataKey.IsCamera, false) })
                }
                .subscribe(plugin)
    }
}