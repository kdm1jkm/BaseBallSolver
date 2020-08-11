package com.github.kdm1jkm.baseballSolver.condition.SingleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

class BallCondition(override val num: Int, override val pos: Int) : Cloneable, SingleCondition {

    override fun isTrue(nums: IntArray): Boolean {
        if (nums[pos] == num) return false
        for (num in nums) if (num == this.num) return true
        return false
    }

    override val sort: ConditionSort
        get() = ConditionSort.Ball


    public override fun clone(): Any {
        return super<SingleCondition>.clone()
    }
}