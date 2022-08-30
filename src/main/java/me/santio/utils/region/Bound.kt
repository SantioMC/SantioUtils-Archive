package me.santio.utils.region

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.util.Vector

class Bound(var corner1: Vector, var corner2: Vector) {
    var minX = 0.0
    var minY = 0.0
    var minZ = 0.0
    var maxX = 0.0
    var maxY = 0.0
    var maxZ = 0.0

    init {
        update()
    }

    private fun update() {
        minX = corner1.x.coerceAtMost(corner2.x)
        minY = corner1.y.coerceAtMost(corner2.y)
        minZ = corner1.z.coerceAtMost(corner2.z)
        maxX = corner1.x.coerceAtLeast(corner2.x)
        maxY = corner1.y.coerceAtLeast(corner2.y)
        maxZ = corner1.z.coerceAtLeast(corner2.z)
    }

    fun getBlocksWithin(world: World?): List<Block> {
        val blocks = ArrayList<Block>()
        var x = minX
        while (x <= maxX) {
            var y = minY
            while (y <= maxY) {
                var z = minZ
                while (z <= maxZ) {
                    blocks.add(Location(world, x, y, z).block)
                    z++
                }
                y++
            }
            x++
        }
        return blocks
    }

    fun corner1(corner1: Vector): Bound {
        this.corner1 = corner1
        update()
        return this
    }

    fun corner2(corner2: Vector): Bound {
        this.corner2 = corner2
        update()
        return this
    }

    fun shift(shift: Vector): Bound {
        corner1.add(shift)
        corner2.add(shift)
        update()
        return this
    }

    fun expand(size: Double): Bound {
        minX -= size
        minY -= size
        minZ -= size
        maxX += size
        maxY += size
        maxZ += size
        corner1 = Vector(minX, minY, minZ)
        corner2 = Vector(maxX, maxY, maxZ)
        return this
    }

    fun expand(extension: Extension): Bound {
        corner1 = extension.affectMin(corner1)!!
        corner2 = extension.affectMax(corner2)!!
        update()
        return this
    }

    fun side(side: Side): ArrayList<Vector> {
        val blocks = ArrayList<Vector>()
        val corner1 = side.getCorner1(this)
        val corner2 = side.getCorner2(this)
        val direction = corner2.clone().subtract(corner1).normalize()
        blocks.add(corner1)
        while (corner1.distance(corner2) > 1) {
            corner1.add(direction)
            blocks.add(corner1.clone())
        }
        blocks.add(corner1)
        return blocks
    }

    fun collides(vector: Vector): Boolean {
        return vector.x in minX..maxX && vector.y in minY..maxY && vector.z in minZ..maxZ
    }

    fun copy(): Bound {
        return Bound(corner1, corner2)
    }

    enum class Side {
        NORTH, EAST, SOUTH, WEST;

        fun getCorner1(bound: Bound): Vector {
            return when (this) {
                NORTH -> Vector(bound.minX, 0.0, bound.minZ)
                EAST -> Vector(bound.maxX, 0.0, bound.minZ)
                SOUTH -> Vector(bound.maxX, 0.0, bound.maxZ)
                WEST -> Vector(bound.minX, 0.0, bound.maxZ)
            }
        }

        fun getCorner2(bound: Bound): Vector {
            return when (this) {
                NORTH -> getCorner1(bound)
                EAST -> getCorner1(bound)
                SOUTH -> getCorner1(bound)
                WEST -> getCorner1(bound)
            }
        }
    }

    enum class Extension(private val min: Int, private val max: Int) {
        VERTICAL(0, 255);

        fun affectMin(original: Vector): Vector? {
            return if (this == VERTICAL) original.clone().setY(min) else null
        }

        fun affectMax(original: Vector): Vector? {
            return if (this == VERTICAL) original.clone().setY(max) else null
        }
    }
}