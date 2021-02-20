package net.kunmc.lab.theworld.effect

import net.kunmc.lab.theworld.rx.Observable
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class TimeStopEffect(private val plugin: JavaPlugin) : Effect() {
    override fun execute(player: Player) {
        Observable.interval(2)
                .take(16)
                .doOnNext {
                    when (it) {
                        0L -> {
                            player.sendTitle("${ChatColor.GREEN}ザ・ワールド", "世界", 1, 100, 1)
                            player.playSound(player.location, Sound.ITEM_TOTEM_USE, 0.3f, 0.4f)
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.A_MINUS_S.pitch)
                        }
                        3L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.A_MINUS.pitch)
                        }
                        7L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.G_MINUS_S.pitch)
                        }
                        11L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.G_MINUS.pitch)
                        }
                        15L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.F_MINUS_S.pitch)
                        }
                    }
                }
                .subscribe(plugin)
    }
}