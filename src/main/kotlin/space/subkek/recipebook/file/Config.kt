package space.subkek.recipebook.file

import org.bukkit.Material
import space.subkek.recipebook.RecipeBook
import space.subkek.sklib.config.Config
import space.subkek.sklib.config.ConfigField
import space.subkek.sklib.config.provider.ConfigProvider
import space.subkek.sklib.config.provider.toml.TomlConfig
import java.io.File

@Config
class Config {
  // Конфиг со старого плагина отношения пока поля не имеют к реальности

  @Config
  class TextureConfig {
    @ConfigField
    val beltBagCustomModelData: Int = 300

    @ConfigField
    val leggingsCustomModelData: Int = 300
  }

  @ConfigField
  val beltBagItemMaterial: Material = Material.PAPER

  @ConfigField
  val texture: TextureConfig = TextureConfig()

  companion object {
    fun load(plugin: RecipeBook): Config {
      val pluginDirectory = plugin.dataFolder

      val configFile = File(pluginDirectory, "config.toml")

      return toml.load<Config>(Config::class.java, configFile, false)
        .also { toml.save(Config::class.java, it, configFile) }
    }

    private val toml = ConfigProvider.getProvider<ConfigProvider>(
      TomlConfig::class.java
    )
  }
}
