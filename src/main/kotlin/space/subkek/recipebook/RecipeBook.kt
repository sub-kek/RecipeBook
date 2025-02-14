package space.subkek.recipebook

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import space.subkek.recipebook.command.RecipesCommand

class RecipeBook : JavaPlugin() {
  companion object {
    val plugin: RecipeBook get() = getPlugin(RecipeBook::class.java)
    val logger: Logger get() = plugin.slF4JLogger
  }

  object Keys {
    class TypedKey<P, C>(key: String, val dataType: PersistentDataType<P, C>) {
      val key: NamespacedKey = NamespacedKey(plugin, key)
    }

    class Key(key: String) {
      val key: NamespacedKey = NamespacedKey(plugin, key)
    }
  }

  private fun registerEvents() {
    // Да да одна из самых важных вещей где
  }

  private fun registerCommands() {
    RecipesCommand().register(this)
  }

  override fun onEnable() {
    registerEvents()
    registerCommands()
  }
}
