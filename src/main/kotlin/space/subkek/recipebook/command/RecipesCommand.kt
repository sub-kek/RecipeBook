package space.subkek.recipebook.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandArguments
import org.bukkit.entity.Player
import space.subkek.recipebook.gui.RecipeGui

class RecipesCommand : CommandAPICommand("recipes") {
  init {
    this.withAliases("crafts")
    this.executesPlayer(this::execute)
  }

  @Suppress("UNUSED_PARAMETER")
  private fun execute(player: Player, arguments: CommandArguments) {
    RecipeGui.open(player)
  }
}
