package space.subkek.recipebook.gui

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.*
import org.bukkit.inventory.RecipeChoice.ExactChoice
import org.bukkit.inventory.RecipeChoice.MaterialChoice
import space.subkek.recipebook.Language

class RecipeGuiHolder(val recipes: List<Recipe>) : InventoryHolder {
  private val inventory: Inventory =
    Bukkit.createInventory(this, RecipeGui.GUI_SIZE, Language.RECIPE_GUI_TITLE.component())

  override fun getInventory(): Inventory {
    return inventory
  }

  init {
    update()
  }

  fun display(index: Int) {
    if (index >= recipes.size) return

    inventory.clear()
    displaying = true

    for (i in 0 until RecipeGui.RECIPES_PER_PAGE) {
      inventory.setItem(i, createBackGroundItem())
    }
    updateNavItems()

    when (val recipe = recipes[index]) {
      is ShapedRecipe -> displayShaped(recipe)
      is ShapelessRecipe -> displayShapeless(recipe)
      else -> update()
    }
  }

  private fun clear3x3Area() {
    for (i in 0 until 9) {
      val row = i / 3
      val col = i % 3

      inventory.setItem(10 + row * 9 + col, ItemStack.empty())
    }
  }

  private fun displayShaped(recipe: ShapedRecipe) {
    clear3x3Area()
    val ingredients = recipe.choiceMap
    for ((rowIndex, row) in recipe.shape.withIndex()) {
      for ((colIndex, char) in row.withIndex()) {
        val item = when (val choice = ingredients[char]) {
          is MaterialChoice -> choice.itemStack
          is ExactChoice -> choice.itemStack
          else -> null
        }
        inventory.setItem(10 + rowIndex * 9 + colIndex, item)
      }
    }
    inventory.setItem(25, recipe.result)

    reopen()
  }

  private fun displayShapeless(recipe: ShapelessRecipe) {
    clear3x3Area()
    val ingredients = recipe.choiceList
    for ((index, choice) in ingredients.withIndex()) {
      val item = when (choice) {
        is MaterialChoice -> choice.itemStack
        is ExactChoice -> choice.itemStack
        else -> null
      }
      val row = index / 3
      val col = index % 3
      inventory.setItem(10 + row * 9 + col, item)
    }
    inventory.setItem(25, recipe.result)

    reopen()
  }

  var page = 0
    private set

  private fun updateNavItems() {
    if (displaying) {
      inventory.setItem(49, RecipeGui.createNavigationItem(Language.EXIT.component(), Material.SPRUCE_DOOR))
    } else {
      if (page > 0)
        inventory.setItem(45, RecipeGui.createNavigationItem(Language.PREVIOUS_PAGE.component(), Material.ARROW))

      val maxPage = (recipes.size + RecipeGui.RECIPES_PER_PAGE - 1) / RecipeGui.RECIPES_PER_PAGE - 1
      if (page < maxPage)
        inventory.setItem(53, RecipeGui.createNavigationItem(Language.NEXT_PAGE.component(), Material.ARROW))

      inventory.setItem(
        49,
        RecipeGui.createNavigationItem(Language.CURRENT_PAGE.component(page, maxPage), Material.NETHER_STAR)
      )
    }
  }

  var displaying = false
    private set

  fun update() {
    displaying = false
    setPage(page)
  }

  fun reopen() {
    inventory.viewers.forEach {
      it.openInventory(inventory)
    }
  }

  fun setPage(page: Int) {
    if (page < 0) return
    val maxPage = (recipes.size + RecipeGui.RECIPES_PER_PAGE - 1) / RecipeGui.RECIPES_PER_PAGE - 1
    if (page > maxPage) return
    this.page = page

    val startIndex = page * RecipeGui.RECIPES_PER_PAGE
    val endIndex = minOf(startIndex + RecipeGui.RECIPES_PER_PAGE, recipes.size)

    inventory.clear()

    for (i in startIndex until endIndex) {
      inventory.setItem(i - startIndex, recipes[i].result)
    }

    updateNavItems()
    reopen()
  }

  private fun createBackGroundItem(): ItemStack {
    val result = ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)
    result.itemMeta.let {
      it.itemName(Component.text(""))
      result.itemMeta = it
    }
    return result
  }
}
