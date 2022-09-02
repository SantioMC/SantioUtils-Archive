package me.santio.utils.bukkit

import me.santio.utils.item.CustomItem
import me.santio.utils.text.Message
import me.santio.utils.text.colored
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BookMeta
import java.util.Collections.max

@Suppress("unused")
class Book {

    private val book = CustomItem(Material.WRITTEN_BOOK)
    private var title: String = "Blank"
    private var author: String = "Blank"
    private var pages: MutableMap<Int, Message> = mutableMapOf()

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
        pages[page] = Message(value.colored())
        update()
        return this
    }

    fun page(page: Int, value: Message): Book {
        pages[page] = value
        update()
        return this
    }

    fun pages(vararg pages: String): Book {
        this.pages = pages.map { Message(it.colored()) }.mapIndexed { index, message -> index to message }.toMap().toMutableMap()
        update()
        return this
    }

    fun pages(vararg pages: Message): Book {
        this.pages = pages.mapIndexed { index, message -> index to message }.toMap().toMutableMap()
        update()
        return this
    }

    private fun<T> toList(map: Map<Int, T>): List<T?> {
        val m = mutableMapOf<Int, T?>()
        for (i in 0 .. max(m.keys)) {
            if (m[i] == null) m[i] = null
        }
        return m.values.toList()
    }

    private fun update() {
        val meta = book.itemMeta as BookMeta
        meta.title = title
        meta.author = author
        meta.spigot().setPages(toList(pages).map { it?.component() ?: Message.EMPTY.component() }.toTypedArray())
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