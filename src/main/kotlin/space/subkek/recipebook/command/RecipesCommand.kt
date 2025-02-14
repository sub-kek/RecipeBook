package space.subkek.recipebook.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandArguments
import org.bukkit.entity.Player
import space.subkek.recipebook.gui.RecipeGUI

class RecipesCommand : CommandAPICommand("recipes") {
  init {
    this.withAliases("crafts")
    this.executesPlayer(this::execute)
  }

  private fun execute(player: Player, arguments: CommandArguments) {
    RecipeGUI.open(player)
  }
}
