package net.kunmc.lab.theworld.command

import net.kunmc.lab.theworld.TheWorld
import net.kunmc.lab.theworld.effect.StopCameraEffect
import net.kunmc.lab.theworld.effect.TimeStopEffect
import net.kunmc.lab.theworld.ext.getMeta
import net.kunmc.lab.theworld.ext.setMeta
import net.kunmc.lab.theworld.metadata.MetadataKey
import net.kunmc.lab.theworld.model.Stop
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DebugCommand(private val plugin: TheWorld) : BaseCommand(plugin) {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: CommandArgs): Boolean {
        return when (Action.find(args[0])) {
            Action.SOUND -> {
                TimeStopEffect(plugin).execute(player)
                true
            }
            Action.STOP -> {
                Stop(plugin).execute(player, 10)
                true
            }
            Action.CAMERA -> {
                player.setMeta(plugin, MetadataKey.IsStopped, !player.getMeta(MetadataKey.IsStopped, false))
                StopCameraEffect(plugin).execute(player)
                true
            }
            else -> false
        }
    }

    override fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: CommandArgs): Boolean {
        sender.sendMessage("You must be a player!")
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        when (args.size) {
            1 -> {
                val keys = Action.values().map { it.value }
                return keys.filter { it.startsWith(args[0]) }
            }
            2 -> {
                return when (Action.find(args[0])) {
                    Action.SOUND -> {
                        emptyList()
                    }
                    Action.STOP -> {
                        emptyList()
                    }
                    Action.CAMERA -> {
                        emptyList()
                    }
                    else -> {
                        emptyList()
                    }
                }
            }
            else -> {
                return emptyList()
            }
        }
    }

    private enum class Action(val value: String) {
        SOUND("sound"),
        STOP("stop"),
        CAMERA("camera");

        companion object {
            fun find(value: String?): Action? {
                return values().firstOrNull { it.value == value }
            }
        }
    }
}