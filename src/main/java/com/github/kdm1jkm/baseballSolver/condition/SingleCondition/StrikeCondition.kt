package com.github.kdm1jkm.baseballSolver.condition.singleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

class StrikeCondition(override val num: Int, override val pos: Int) : SingleCondition, Cloneable {
    override fun isTrue(nums: IntArray): Boolean = nums[pos] == num

    override val sort: ConditionSort
        get() = ConditionSort.Strike
}