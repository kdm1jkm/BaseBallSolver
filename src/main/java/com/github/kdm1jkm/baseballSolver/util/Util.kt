package com.github.kdm1jkm.baseballSolver.util

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.*
import com.github.kdm1jkm.baseballSolver.debug.Debug

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

    fun getBaseballGameScore(rightAnswer: List<Int>, userAnswer: List<Int>): MutableMap<ConditionSort, Int> {
        val result = mutableMapOf(
                Strike to 0,
                Ball to 0,
                Out to 0
        )

        userAnswer.forEachIndexed { i, num ->
            when {
                rightAnswer[i] == num ->
                    result[Strike] = result[Strike]!! + 1

                rightAnswer.contains(num) ->
                    result[Ball] = result[Ball]!! + 1

                else ->
                    result[Out] = result[Out]!! + 1
            }
        }
        return result
    }
}