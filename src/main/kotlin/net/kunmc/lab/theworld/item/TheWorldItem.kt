package team.kun.wraith.item

import net.kunmc.lab.theworld.TheWorld
import net.kunmc.lab.theworld.item.Craftable
import net.kunmc.lab.theworld.item.Item
import net.kunmc.lab.theworld.model.Stop
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class TheWorldItem : Item(), Craftable {
    override val name = "ザ・ワールド（世界）"
    override val description = listOf(
            "時間の流れを停止させることできる。",
            "また止まった時間の中で動くことができる"
    )
    override val itemStack = ItemStack(Material.BLAZE_POWDER)

    val seconds = 10

    override fun getRecipe(plugin: JavaPlugin): ShapedRecipe? {
        val key = getKey(plugin) ?: return null
        return ShapedRecipe(key, toItemStack(plugin)).apply {
            shape("OOO", "ODO", "OOO")
            setIngredient('O', Material.OBSIDIAN)
            setIngredient('D', Material.DIAMOND_BLOCK)
        }
    }

    fun execute(player: Player, itemStack: ItemStack, plugin: TheWorld) {
        itemStack.amount -= 1
        Stop(plugin).execute(player, seconds)
    }
}