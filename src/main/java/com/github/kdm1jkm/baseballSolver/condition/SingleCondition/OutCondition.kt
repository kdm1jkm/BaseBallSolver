package com.github.kdm1jkm.baseballSolver.condition.SingleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

class OutCondition(override val num: Int) : SingleCondition, Cloneable {

    override val pos: Int
        get() = -1

    override fun isTrue(nums: IntArray): Boolean {
        for (num in nums) if (num == this.num) return false
        return true
    }

    override val sort: ConditionSort
        get() = ConditionSort.Out

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super<SingleCondition>.clone()
    }

}