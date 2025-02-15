package space.subkek.recipebook.gui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class RecipeGuiListener : Listener {
  @EventHandler
  private fun onClick(event: InventoryClickEvent) {
    // Проверить что сущность - игрок и получить его
    val player = event.whoClicked
    if (player !is Player) return

    if (!RecipeGui.playersOpened.contains(player)) return
    event.isCancelled = true
    val holder = event.inventory.holder ?: return

    if (holder is RecipeGuiHolder) {
      val controlSlot = event.slot - RecipeGui.RECIPES_PER_PAGE

      when (controlSlot) {
        0 -> { // Назад
          holder.setPage(player, holder.page - 1)
        }

        4 -> { // Обновить
          holder.setPage(player, holder.page)
        }

        8 -> { // Вперед
          holder.setPage(player, holder.page + 1)
        }
      }
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
