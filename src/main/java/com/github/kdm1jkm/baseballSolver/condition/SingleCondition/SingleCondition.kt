package com.github.kdm1jkm.baseballSolver.condition.singleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

interface SingleCondition : Cloneable {
    val num: Int
    val pos: Int
    fun isTrue(nums: IntArray): Boolean
    val sort: ConditionSort
}