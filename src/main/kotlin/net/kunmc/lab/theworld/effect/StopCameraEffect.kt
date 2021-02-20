package net.kunmc.lab.theworld.effect

import net.kunmc.lab.theworld.ext.setMeta
import net.kunmc.lab.theworld.ext.spawn
import net.kunmc.lab.theworld.metadata.MetadataKey
import net.kunmc.lab.theworld.packet.CameraPacket
import org.bukkit.entity.Enderman
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class StopCameraEffect(private val plugin: JavaPlugin) : Effect() {
    override fun execute(player: Player) {
        val camera = player.location.spawn<Enderman> {
            it.setAI(false)
            it.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 10000, 2))
            it.isCollidable = false
            it.isInvulnerable = true
            it.setMeta(plugin, MetadataKey.IsCamera, true)
        } as Enderman
        CameraPacket(camera).send(player)
        player.setMeta(plugin, MetadataKey.Camera, camera)
    }
}