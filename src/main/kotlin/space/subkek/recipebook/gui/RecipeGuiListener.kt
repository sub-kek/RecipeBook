package space.subkek.recipebook.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice.ExactChoice
import org.bukkit.inventory.RecipeChoice.MaterialChoice
import org.bukkit.inventory.ShapedRecipe

class RecipeGuiListener : Listener {
  @EventHandler
  private fun onClick(event: InventoryClickEvent) {
    val player = event.whoClicked
    if (player !is Player) return

    if (!RecipeGui.playersOpened.contains(player)) return
    event.isCancelled = true
    val holder = event.clickedInventory?.holder ?: return

    if (holder is RecipeGuiHolder) {
      if (holder.displaying) {
        val controlSlot = event.slot - RecipeGui.RECIPES_PER_PAGE
        when (controlSlot) {
          4 -> holder.update()
        }
      } else if (event.slot < RecipeGui.RECIPES_PER_PAGE) {
        // Display recipe
        val recipeIndex = RecipeGui.RECIPES_PER_PAGE * holder.page + event.slot
        holder.display(recipeIndex)
      } else {
        val controlSlot = event.slot - RecipeGui.RECIPES_PER_PAGE

        when (controlSlot) {
          0 -> { // Назад
            holder.setPage(holder.page - 1)
          }

          8 -> { // Вперед
            holder.setPage(holder.page + 1)
          }
        }
      }
    }
  }

  // Добавить игрока в список тех, кто открыл gui
  @EventHandler
  private fun onOpen(event: InventoryOpenEvent) {
    val player = event.player
    if (player !is Player) return

    event.inventory.holder.let {
      if (it is RecipeGuiHolder) RecipeGui.playersOpened.add(player)
    }
  }

  // Убрать игрока из списка тех, кто открыл gui
  @EventHandler
  private fun onClose(event: InventoryCloseEvent) {
    val player = event.player
    if (player !is Player) return

    event.inventory.holder.let {
      if (it is RecipeGuiHolder) RecipeGui.playersOpened.remove(player)
    }
  }
}
