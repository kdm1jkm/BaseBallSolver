package com.github.kdm1jkm.baseballSolver

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

class GameHost(val nums: List<Int>) {
    companion object {
        @JvmStatic
        fun getBaseballGameScore(rightAnswer: List<Int>, userAnswer: List<Int>): MutableMap<ConditionSort, Int> {
            val result = mutableMapOf(
                    ConditionSort.Strike to 0,
                    ConditionSort.Ball to 0,
                    ConditionSort.Out to 0
            )

            userAnswer.forEachIndexed { i, num ->
                when {
                    rightAnswer[i] == num ->
                        result[ConditionSort.Strike] = result[ConditionSort.Strike]!! + 1

                    rightAnswer.contains(num) ->
                        result[ConditionSort.Ball] = result[ConditionSort.Ball]!! + 1

                    else ->
                        result[ConditionSort.Out] = result[ConditionSort.Out]!! + 1
                }
            }
            return result
        }
    }

    fun getScore(answer: List<Int>): MutableMap<ConditionSort, Int> {
        return getBaseballGameScore(nums, answer)
    }
}