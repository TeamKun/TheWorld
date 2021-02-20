package net.kunmc.lab.theworld.listener

import net.kunmc.lab.theworld.TheWorld
import net.kunmc.lab.theworld.ext.getMeta
import net.kunmc.lab.theworld.metadata.MetadataKey
import net.kunmc.lab.theworld.model.Stop
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import team.kun.wraith.item.TheWorldItem

class PlayerListener(private val plugin: TheWorld) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (plugin.isStopped) {
            Stop(plugin).stopEntity(player)
        } else {
            Stop(plugin).startEntity(player)
        }
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val itemStack = event.item
        if (TheWorldItem().equal(itemStack, plugin)) {
            itemStack ?: return
            TheWorldItem().execute(player, itemStack, plugin)
            event.isCancelled = true
        } else if (player.getMeta(MetadataKey.IsStopped, false)) {
            event.isCancelled = true
        }
    }
}