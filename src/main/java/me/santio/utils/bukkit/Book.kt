package me.santio.utils.bukkit

import me.santio.utils.item.CustomItem
import me.santio.utils.text.Message
import me.santio.utils.text.colored
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BookMeta

@Suppress("unused")
class Book {

    private val book = CustomItem(Material.WRITTEN_BOOK)
    private var title: String = "Blank"
    private var author: String = "Blank"
    private var pages: MutableList<Message> = mutableListOf()

    init {
        update()
    }

    fun title(title: String): Book {
        this.title = title.colored()
        update()
        return this
    }

    fun author(author: String): Book {
        this.author = author.colored()
        update()
        return this
    }

    fun page(value: String): Book {
        pages.add(Message(value))
        update()
        return this
    }

    fun page(value: Message): Book {
        pages.add(value)
        update()
        return this
    }

    fun pages(vararg pages: String): Book {
        this.pages = pages.map { Message(it) }.toMutableList()
        update()
        return this
    }

    fun pages(vararg pages: Message): Book {
        this.pages = pages.toMutableList()
        update()
        return this
    }

    private fun update() {
        val meta = book.itemMeta as BookMeta
        meta.title = title
        meta.author = author
        meta.spigot().setPages(pages.map { it.component() }.toTypedArray())
        book.itemMeta = meta
    }

    fun give(vararg players: Player): Book {
        players.forEach { it.inventory.addItem(book) }
        return this
    }

    fun open(vararg players: Player): Book {
        players.forEach { it.openBook(book) }
        return this
    }

}