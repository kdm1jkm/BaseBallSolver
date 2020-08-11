package com.github.kdm1jkm.baseballSolver.record

import com.github.kdm1jkm.baseballSolver.condition.Condition
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.Companion.getList
import com.github.kdm1jkm.baseballSolver.util.Util
import java.util.*
import kotlin.system.exitProcess

class Record(val nums: IntArray, strike: Int, ball: Int) {
    private val out: Int = nums.size - strike - ball
    val data: MutableSet<Condition> = HashSet()

    init {
        makeConditions(nums, strike, ball, out)
    }

    fun isTrue(nums: IntArray): Boolean {
        for (condition in data) if (condition.isTrue(nums)) return true
        return false
    }

    private fun makeConditions(nums: IntArray, strike: Int, ball: Int, out: Int) {
        var permutations: List<List<ConditionSort>>
        run {
            val counts: MutableMap<ConditionSort, Int> = EnumMap(ConditionSort::class.java)
            counts[ConditionSort.Strike] = strike
            counts[ConditionSort.Ball] = ball
            counts[ConditionSort.Out] = out
            permutations = Util.getPermutations(getList(counts), nums.size)
        }
        // 각 경우에 대해
        for (permutation in permutations) {
            val condition = Condition()
            for (i in permutation.indices) {
                try {
                    condition.addCondition(permutation[i].getInstance(nums[i], i))
                } catch (e: Exception) {
                    e.printStackTrace()
                    exitProcess(-1)
                }
            }
            data.add(condition)
        }
    }
}