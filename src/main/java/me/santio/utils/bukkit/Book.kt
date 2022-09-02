package me.santio.utils.bukkit

import me.santio.utils.item.CustomItem
import me.santio.utils.text.colored
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BookMeta

@Suppress("unused")
class Book {

    private val book = CustomItem(Material.WRITTEN_BOOK)
    private var title: String = "Blank"
    private var author: String = "Blank"
    private var pages: MutableList<String> = mutableListOf()

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

    fun page(page: Int, value: String): Book {
        pages[page] = value.colored()
        update()
        return this
    }

    fun page(page: Int, vararg value: String): Book {
        pages[page] = value.joinToString("\n").colored()
        update()
        return this
    }

    fun pages(vararg pages: String): Book {
        this.pages = pages.map { it.colored() }.toMutableList()
        update()
        return this
    }

    private fun update() {
        val meta = book.itemMeta as BookMeta
        meta.title = title
        meta.author = author
        meta.pages = pages
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