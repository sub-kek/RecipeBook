package space.subkek.recipebook.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandArguments
import org.bukkit.Bukkit
import org.bukkit.Keyed
import org.bukkit.entity.Player
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import space.subkek.recipebook.gui.RecipeGui

class RecipesCommand : CommandAPICommand("recipes") {
  init {
    this.withAliases("crafts")

    this.withSubcommands(object : CommandAPICommand("minecraft") {
      init {
        this.executesPlayer(this::execute)
      }

      @Suppress("UNUSED_PARAMETER")
      private fun execute(player: Player, arguments: CommandArguments) {
        val recipes = getMinecraftRecipes().sortedBy { it.result.type.name }
        RecipeGui.open(player, recipes)
      }
    })

    this.executesPlayer(this::execute)
  }

  @Suppress("UNUSED_PARAMETER")
  private fun execute(player: Player, arguments: CommandArguments) {
    val recipes = getCustomRecipes().sortedBy { it.result.type.name }
    RecipeGui.open(player, recipes)
  }

  private fun getMinecraftRecipes(): List<Recipe> {
    return Bukkit.recipeIterator().asSequence()
      .filter {
        it is Keyed && it.key.namespace == "minecraft" &&
            (it is ShapelessRecipe || it is ShapedRecipe)
      } // Фильтруем по namespace
      .toList()
  }

  private fun getCustomRecipes(): List<Recipe> {
    return Bukkit.recipeIterator().asSequence()
      .filter {
        it is Keyed && it.key.namespace != "minecraft" &&
            (it is ShapelessRecipe || it is ShapedRecipe)
      } // Фильтруем по namespace
      .toList()
  }
}
