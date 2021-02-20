package net.kunmc.lab.theworld.effect

import net.kunmc.lab.theworld.rx.Observable
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class TimeStartEffect(private val plugin: JavaPlugin) : Effect() {
    override fun execute(player: Player) {
        Observable.interval(2)
                .take(8)
                .doOnNext {
                    when (it) {
                        0L -> {
                            player.sendTitle("時は動き出す", "", 1, 100, 1)
                            player.playSound(player.location, Sound.BLOCK_PORTAL_TRIGGER, 0.3f, 2.0f)
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.F_MINUS_S.pitch)
                        }
                        2L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.G_MINUS.pitch)
                        }
                        4L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.G_MINUS_S.pitch)
                        }
                        6L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.A_MINUS.pitch)
                        }
                        7L -> {
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 2.0f, MusicScale.A_MINUS_S.pitch)
                        }
                    }
                }
                .subscribe(plugin)
    }
}