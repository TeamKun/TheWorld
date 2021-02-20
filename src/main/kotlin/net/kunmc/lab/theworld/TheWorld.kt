package net.kunmc.lab.theworld

import net.kunmc.lab.theworld.command.DebugCommand
import net.kunmc.lab.theworld.command.SettingCommand
import net.kunmc.lab.theworld.ext.initCommand
import net.kunmc.lab.theworld.ext.registerListener
import net.kunmc.lab.theworld.listener.BlockListener
import net.kunmc.lab.theworld.listener.EntityListener
import net.kunmc.lab.theworld.listener.PlayerListener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.author.Author
import team.kun.wraith.item.RecipeService

@Plugin(name = "TheWorld", version = "1.0-SNAPSHOT")
@Author("ReyADayer")
@ApiVersion(ApiVersion.Target.v1_15)
@Commands(
        Command(
                name = PluginCommands.DEBUG,
                desc = "debug command",
                usage = "/<command>",
                permission = PluginPermissions.ADMIN,
                permissionMessage = "You don't have <permission>"
        ),
        Command(
                name = PluginCommands.SETTING,
                desc = "setting command",
                usage = "/<command>",
                permission = PluginPermissions.ADMIN,
                permissionMessage = "You don't have <permission>"
        )
)
class TheWorld : JavaPlugin() {
    var isStopped = false
    var seconds = 5

    override fun onEnable() {
        this.initCommand(PluginCommands.DEBUG, DebugCommand(this))
        this.initCommand(PluginCommands.SETTING, SettingCommand(this))

        this.registerListener(BlockListener(this))
        this.registerListener(EntityListener(this))
        this.registerListener(PlayerListener(this))

        RecipeService.add(this)
    }

    override fun onDisable() {
        RecipeService.remove(this)
    }
}