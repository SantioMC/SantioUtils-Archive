@file:JvmName("LocationUtils")
package me.santio.utils.bukkit

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.entity.Entity
import java.util.function.Predicate

fun Location.getAsyncEntities(predicate: Predicate<Entity>? = null): Set<Entity> {
    return getChunks().map { chunk -> chunk.entities.filter { b -> predicate?.test(b) ?: true } }.flatten().toSet()
}

fun Location.getChunks(radius: Int = 2): Set<Chunk> {
    val chunks: MutableSet<Chunk> = mutableSetOf()

    chunks.add(this.chunk)
    chunks.add(this.clone().add(radius.toDouble(), 0.0, 0.0).chunk)
    chunks.add(this.clone().add(-radius.toDouble(), 0.0, 0.0).chunk)
    chunks.add(this.clone().add(0.0, 0.0, radius.toDouble()).chunk)
    chunks.add(this.clone().add(0.0, 0.0, -radius.toDouble()).chunk)

    return chunks
}