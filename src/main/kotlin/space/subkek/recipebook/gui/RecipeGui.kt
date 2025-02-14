package space.subkek.recipebook.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe

object RecipeGUI {
  private const val GUI_SIZE = 54
  private const val GUI_TITLE = "Custom Recipes"

  fun open(player: Player) {
    val inventory = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE)

    val recipes = getCustomRecipes()
    for ((index, recipe) in recipes.withIndex()) {
      if (index >= GUI_SIZE) break
      inventory.setItem(index, createRecipeItem(recipe))
    }

    player.openInventory(inventory)
  }

  private fun getCustomRecipes(): List<Recipe> {
    return Bukkit.recipeIterator().asSequence()
      .filter { it is ShapedRecipe && it.key.namespace == "custom_namespace" } // Фильтруем по namespace
      .toList()
  }

  private fun createRecipeItem(recipe: Recipe): ItemStack {
    val result = recipe.result
    val meta = result.itemMeta
    meta?.setDisplayName("§e${result.type.name} Recipe")
    result.itemMeta = meta
    return result
  }
}
