@file:JvmName("HitboxUtils")
@file:Suppress("unused", "DuplicatedCode")

package me.santio.utils.bukkit

import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

fun Vector.isInBoundingBox(box: BoundingBox): Boolean {
    return this.x >= box.minX && this.x <= box.maxX
        && this.y >= box.minY && this.y <= box.maxY
        && this.z >= box.minZ && this.z <= box.maxZ
}

fun BoundingBox.color(world: World, particle: Particle?) {
    for (vector in this.getCorners()) {
        world.spawnParticle(particle!!, vector.toLocation(world), 1, 0.0, 0.0, 0.0, 0.0)
    }
}

fun BoundingBox.getCorners(): List<Vector> {
    val vectors: MutableList<Vector> = mutableListOf()

    // Top 4
    vectors.add(Vector(this.minX, this.maxY, this.minZ))
    vectors.add(Vector(this.minX, this.maxY, this.maxZ))
    vectors.add(Vector(this.maxX, this.maxY, this.minZ))
    vectors.add(Vector(this.maxX, this.maxY, this.maxZ))

    // Bottom 4
    vectors.add(Vector(this.minX, this.minY, this.minZ))
    vectors.add(Vector(this.minX, this.minY, this.maxZ))
    vectors.add(Vector(this.maxX, this.minY, this.minZ))
    vectors.add(Vector(this.maxX, this.minY, this.maxZ))

    return vectors
}