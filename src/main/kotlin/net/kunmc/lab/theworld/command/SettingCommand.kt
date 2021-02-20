package net.kunmc.lab.theworld.command

import net.kunmc.lab.theworld.TheWorld
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SettingCommand(private val plugin: TheWorld) : BaseCommand(plugin) {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: CommandArgs): Boolean {
        val seconds = args[0]?.toIntOrNull() ?: return false
        plugin.seconds = seconds
        plugin.server.broadcastMessage("停止時間を${seconds}に変更しました")
        return true
    }

    override fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: CommandArgs): Boolean {
        sender.sendMessage("You must be a player!")
        return false
    }
}