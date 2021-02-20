package net.kunmc.lab.theworld.effect

import net.kunmc.lab.theworld.ext.getMeta
import net.kunmc.lab.theworld.ext.removeMeta
import net.kunmc.lab.theworld.metadata.MetadataKey
import net.kunmc.lab.theworld.packet.CameraPacket
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ResetCameraEffect(private val plugin: JavaPlugin) : Effect() {
    override fun execute(player: Player) {
        CameraPacket(player).send(player)
        player.getMeta(MetadataKey.Camera)?.remove()
        player.removeMeta(plugin, MetadataKey.Camera)
    }
}