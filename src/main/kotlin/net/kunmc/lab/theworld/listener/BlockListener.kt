package net.kunmc.lab.theworld.listener

import net.kunmc.lab.theworld.TheWorld
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockFormEvent
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.block.BlockSpreadEvent
import org.bukkit.event.block.LeavesDecayEvent

class BlockListener(private val plugin: TheWorld) : Listener {
    @EventHandler
    fun onBlockFromTo(event: BlockFromToEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockIgnite(event: BlockIgniteEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockPhysics(event: BlockPhysicsEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onLeavesDecay(event: LeavesDecayEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockForm(event: BlockFormEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockSpread(event: BlockSpreadEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        if (plugin.isStopped) {
            event.isCancelled = true
        }
    }


}