package team.kun.wraith.item

import net.kunmc.lab.theworld.item.Craftable
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object RecipeService {
    val items: List<Craftable> = listOf(
            TheWorldItem()
    )

    fun add(plugin: JavaPlugin) {
        items.forEach {
            Bukkit.addRecipe(it.getRecipe(plugin))
        }
    }

    fun remove(plugin: JavaPlugin) {
        items.forEach { item ->
            item.getKey(plugin)?.let {
                Bukkit.removeRecipe(it)
            }
        }
    }
}