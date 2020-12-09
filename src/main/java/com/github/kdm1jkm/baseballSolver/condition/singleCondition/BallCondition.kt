package com.github.kdm1jkm.baseballSolver.condition.singleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

class BallCondition(override val num: Int, override val pos: Int) : SingleCondition {
    override fun isTrue(nums: List<Int>): Boolean = nums[pos] != num && nums.any { it == num }

    override val sort: ConditionSort
        get() = ConditionSort.Ball
}