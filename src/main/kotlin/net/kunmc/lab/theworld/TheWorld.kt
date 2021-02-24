package net.kunmc.lab.theworld

import net.kunmc.lab.theworld.command.DebugCommand
import net.kunmc.lab.theworld.command.GiveCommand
import net.kunmc.lab.theworld.command.SettingCommand
import net.kunmc.lab.theworld.ext.initCommand
import net.kunmc.lab.theworld.ext.registerListener
import net.kunmc.lab.theworld.listener.BlockListener
import net.kunmc.lab.theworld.listener.EntityListener
import net.kunmc.lab.theworld.listener.PlayerListener
import org.bukkit.NamespacedKey
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
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
        ),
        Command(
                name = PluginCommands.GIVE,
                desc = "give command",
                usage = "/<command>",
                permission = PluginPermissions.ADMIN,
                permissionMessage = "You don't have <permission>"
        )
)
class TheWorld : JavaPlugin() {
    var isStopped = false
    var seconds = 5
    lateinit var bossBar: BossBar

    override fun onEnable() {
        this.initCommand(PluginCommands.DEBUG, DebugCommand(this))
        this.initCommand(PluginCommands.SETTING, SettingCommand(this))
        this.initCommand(PluginCommands.GIVE, GiveCommand(this))

        this.registerListener(BlockListener(this))
        this.registerListener(EntityListener(this))
        this.registerListener(PlayerListener(this))

        bossBar = this.server.createBossBar(NamespacedKey(this, "TheWorld"), "ザ・ワールド", BarColor.YELLOW, BarStyle.SOLID)

        RecipeService.add(this)
    }

    override fun onDisable() {
        RecipeService.remove(this)
    }
}