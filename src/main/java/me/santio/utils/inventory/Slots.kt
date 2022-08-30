package me.santio.utils.inventory

class Slots(private val slots: MutableSet<Int>) {

    companion object {

        @JvmStatic
        val ALL: Slots = rect(0, 9*9 - 1)

        @JvmStatic
        val NONE: Slots = Slots(mutableSetOf())

        @JvmStatic
        fun get(vararg slots: Int): Slots = Slots(slots.toMutableSet())

        @JvmStatic
        @JvmOverloads
        fun column(column: Int, margin: Int = 0): Slots {
            val slots = mutableSetOf<Int>()
            for (i in margin until 9-margin) {
                slots.add((column - 1) + (i * 9))
            }
            return Slots(slots)
        }

        @JvmStatic
        @JvmOverloads
        fun row(row: Int, margin: Int = 0): Slots {
            val slots = mutableSetOf<Int>()
            for (i in margin until 9-margin) {
                slots.add(i + ((row - 1) * 9))
            }
            return Slots(slots)
        }

        @JvmStatic
        fun rect(corner1: Int, corner2: Int): Slots {
            val slots = mutableSetOf<Int>()
            val oneCoord = getCords(minOf(corner1, corner2))
            val twoCoord = getCords(maxOf(corner1, corner2))

            for (x in oneCoord.first..twoCoord.first) {
                for (y in oneCoord.second..twoCoord.second) {
                    slots.add(x + (y * 9))
                }
            }

            return Slots(slots)
        }

        private fun getCords(slot: Int): Pair<Int, Int> {
            return Pair(slot % 9, slot / 9)
        }

    }

    fun add(vararg slots: Int): Slots {
        slots.forEach { this.slots.add(it) }
        return this
    }

    fun remove(vararg slots: Int): Slots {
        slots.forEach { this.slots.remove(it) }
        return this
    }

    fun add(slots: Slots): Slots {
        this.slots.addAll(slots.slots)
        return this
    }

    fun remove(slots: Slots): Slots {
        this.slots.removeAll(slots.slots)
        return this
    }

    fun size() = slots.size
    fun iterator(): Iterator<Int> = slots.iterator()

    fun get(index: Int): Int? = slots.elementAtOrNull(index)

    fun apply(inventory: CustomInventory): Set<Int> {
        return slots.filter { it < inventory.size() }.toSet()
    }

}