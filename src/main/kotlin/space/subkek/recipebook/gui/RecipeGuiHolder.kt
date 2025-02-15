package space.subkek.recipebook.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import space.subkek.recipebook.Language

class RecipeGuiHolder(private val items: List<ItemStack>) : InventoryHolder {
  private lateinit var inventory: Inventory

  override fun getInventory(): Inventory {
    return inventory
  }

  var page = 0
    private set

  fun setPage(player: Player, page: Int) {
    if (page < 0) return
    val maxPage = (items.size + RecipeGui.RECIPES_PER_PAGE - 1) / RecipeGui.RECIPES_PER_PAGE - 1
    if (page > maxPage) return
    this.page = page

    inventory = Bukkit.createInventory(this, RecipeGui.GUI_SIZE, Language.RECIPE_GUI_TITLE.component(page, maxPage))
    player.openInventory(inventory)
    RecipeGui.playersOpened.add(player)


    val startIndex = page * RecipeGui.RECIPES_PER_PAGE
    val endIndex = minOf(startIndex + RecipeGui.RECIPES_PER_PAGE, items.size)

    for (i in startIndex until endIndex) {
      inventory.setItem(i - startIndex, items[i])
    }

    if (page > 0) inventory.setItem(45, RecipeGui.createNavigationItem("§e⬅ Назад", Material.ARROW))
    inventory.setItem(49, RecipeGui.createNavigationItem("§a🔄 Обновить", Material.NETHER_STAR))
    if (page < maxPage) inventory.setItem(53, RecipeGui.createNavigationItem("§e➡ Вперед", Material.ARROW))
  }
}
