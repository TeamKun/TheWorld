package net.kunmc.lab.theworld.metadata

import net.kunmc.lab.theworld.ext.getMeta
import net.kunmc.lab.theworld.ext.setMeta
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class PlayerFlagMetadata(val plugin: JavaPlugin) {
    fun avoidTwice(player: Player) {
        setFlag(player, true)
        object : BukkitRunnable() {
            override fun run() {
                setFlag(player, false)
            }
        }.runTaskLater(plugin, 10)
    }

    fun setFlag(player: Player, flag: Boolean) {
        player.setMeta(plugin, MetadataKey.IsPlayerInteract, flag)
    }

    fun getFlag(player: Player): Boolean {
        return player.getMeta(MetadataKey.IsPlayerInteract, false)
    }
}