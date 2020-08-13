package com.github.kdm1jkm.baseballSolver.condition

import com.github.kdm1jkm.baseballSolver.condition.singleCondition.SingleCondition

class Condition {
    private val data: MutableSet<SingleCondition> = HashSet()

    fun addCondition(condition: SingleCondition) = data.add(condition)

    fun isTrue(nums: List<Int>): Boolean = data.all { it.isTrue(nums) }

    val conditions: Array<SingleCondition>
        get() = data.toTypedArray()
}