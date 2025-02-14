package space.subkek.recipebook

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

enum class Language(private val content: String) {
  // Yes, it is really stupid way to make language.
  // #TODO Make multi-language support without changing something else
  PREFIX("<#ff5e45>RecipeBook » <#ff8c00> "),
  NO_PERMISSION("<red>У вас нет прав на использование команды.");

  private fun string(): String {
    return this.content
  }

  private fun formattedString(vararg replace: Any?): String {
    return formatString(string(), *replace)
  }

  fun component(vararg replace: Any?): Component {
    return MINI_MESSAGE.deserialize(formattedString(*replace))
  }

  fun pComponent(vararg replace: Any?): Component {
    return MINI_MESSAGE.deserialize(PREFIX.string() + formattedString(*replace))
  }

  companion object {
    private val MINI_MESSAGE = MiniMessage.miniMessage()

    fun formatString(str: String, vararg replace: Any?): String {
      var result = str
      for (i in replace.indices) {
        result = result.replace("{$i}", replace[i].toString())
      }
      return result
    }

    fun deserialize(content: String, vararg replace: Any?): Component {
      return MINI_MESSAGE.deserialize(formatString(content, *replace))
    }
  }
}
