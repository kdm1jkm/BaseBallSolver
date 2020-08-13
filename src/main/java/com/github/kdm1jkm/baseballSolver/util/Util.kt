package com.github.kdm1jkm.baseballSolver.util

object Util {
    @JvmStatic
    fun <T> getPermutations(elements: List<T>, count: Int): List<List<T>> {
        if (count == 1) {
            val result: MutableList<List<T>> = ArrayList(elements.size)
            elements.forEach {
                result.add(listOf(it))
            }
            return result
        }

        if (elements.size == 1) return listOf(elements)

        val overlappedElements: MutableSet<T> = HashSet()
        val result: MutableList<List<T>> = ArrayList()

        for ((i, element) in elements.withIndex()) {
            if (overlappedElements.contains(element)) continue
            overlappedElements.add(element)

            val left: MutableList<T> = ArrayList()
            left.addAll(elements.subList(0, i))
            left.addAll(elements.subList(i + 1, elements.size))

            val back = getPermutations(left, count - 1)
            back.forEach { backPermutation: List<T> ->
                val arr: MutableList<T> = ArrayList()
                arr.add(elements[i])
                arr.addAll(backPermutation)
                result.add(arr)
            }
        }
        return result
    }
}