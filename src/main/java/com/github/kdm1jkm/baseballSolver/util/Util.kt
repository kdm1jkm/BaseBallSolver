package com.github.kdm1jkm.baseballSolver.util

import java.util.*
import java.util.function.Consumer

object Util {
    @JvmStatic
    fun <T> getPermutations(elements: List<T>, count: Int): List<List<T>> {
        if (count == 1) {
            val result: MutableList<List<T>> = ArrayList()
            elements.forEach { t: T ->
                result.add(listOf(t))
            }
            return result
        }

        if (elements.size == 1) return listOf(elements)

        val used: MutableSet<T> = HashSet()
        val result: MutableList<List<T>> = ArrayList()

        for (i in elements.indices) {
            if (used.contains(elements[i])) continue

            used.add(elements[i])
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