package space.subkek.recipebook.gui

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object RecipeGui {
  val playersOpened = mutableSetOf<Player>()

  private const val ROW_SIZE = 9
  const val GUI_SIZE = ROW_SIZE * 6
  const val RECIPES_PER_PAGE = GUI_SIZE - ROW_SIZE

  fun open(player: Player, recipes: List<Recipe>) {
    val holder = RecipeGuiHolder(recipes)
    player.openInventory(holder.inventory)
  }

  fun createNavigationItem(name: Component, material: Material): ItemStack {
    val item = ItemStack(material)
    item.itemMeta.let {
      it.itemName(name)
      item.itemMeta = it
    }
    return item
  }
}
