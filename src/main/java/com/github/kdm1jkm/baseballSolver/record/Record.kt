package com.github.kdm1jkm.baseballSolver.record

import com.github.kdm1jkm.baseballSolver.condition.Condition
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.Companion.getList
import com.github.kdm1jkm.baseballSolver.util.Util

class Record(val nums: List<Int>, val strike: Int, val ball: Int) {
    val out: Int = nums.size - strike - ball
    val data: MutableSet<Condition> = HashSet()

    init {
        makeConditions(nums, strike, ball, out)
    }

    fun isTrue(nums: List<Int>): Boolean = data.any { it.isTrue(nums) }

    private fun makeConditions(nums: List<Int>, strike: Int, ball: Int, out: Int) {
        val conditionSortPermutations: List<List<ConditionSort>> =
                Util.getPermutations(getList(mapOf(
                        Pair(ConditionSort.Strike, strike),
                        Pair(ConditionSort.Ball, ball),
                        Pair(ConditionSort.Out, out)
                )), nums.size)

        // 각 경우에 대해
        for (conditionSortPermutation in conditionSortPermutations) {
            val condition = Condition()
            conditionSortPermutation.forEachIndexed { i, conditionSort ->
                condition.addCondition(conditionSort.getInstance(nums[i], i))
            }
            data.add(condition)
        }
    }
}