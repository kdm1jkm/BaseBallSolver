package com.github.kdm1jkm.baseballSolver.condition.SingleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

class StrikeCondition(override val num: Int, override val pos: Int) : SingleCondition, Cloneable {

    override fun isTrue(nums: IntArray): Boolean {
        return nums[pos] == num
    }

    override val sort: ConditionSort
        get() = ConditionSort.Strike

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super<SingleCondition>.clone()
    }

}