package net.kunmc.lab.theworld.effect

import org.bukkit.entity.Player

abstract class Effect {
    abstract fun execute(player: Player)

    fun execute(players: List<Player>) {
        players.forEach {
            execute(it)
        }
    }
}