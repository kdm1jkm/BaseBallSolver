package com.github.kdm1jkm.baseballSolver.condition.singleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

class OutCondition(override val num: Int, override val pos: Int) : SingleCondition {
    override fun isTrue(nums: List<Int>): Boolean = nums.all { it != num }

    override val sort: ConditionSort
        get() = ConditionSort.Out
}