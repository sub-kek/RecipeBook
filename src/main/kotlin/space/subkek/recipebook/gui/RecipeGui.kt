package space.subkek.recipebook.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.ItemMeta

object RecipeGui {
  val playersOpened = mutableSetOf<Player>()

  private const val ROW_SIZE = 9
  const val GUI_SIZE = ROW_SIZE * 6
  const val RECIPES_PER_PAGE = GUI_SIZE - ROW_SIZE

  fun open(player: Player) {
    val recipes = getCustomRecipes()
    val holder = RecipeGuiHolder(recipes.map { it.result })

    holder.setPage(player, 0)
  }

  private fun getCustomRecipes(): List<Recipe> {
    return Bukkit.recipeIterator().asSequence()
      .filter { it is ShapedRecipe && it.key.namespace == "minecraft" } // Фильтруем по namespace
      .toList()
  }

  fun createNavigationItem(name: String, material: Material): ItemStack {
    val item = ItemStack(material)
    val meta: ItemMeta? = item.itemMeta
    meta?.setDisplayName(name)
    item.itemMeta = meta
    return item
  }
}
