package com.github.kdm1jkm.baseballSolver.condition.singleCondition

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort

interface SingleCondition {
    val num: Int
    val pos: Int
    fun isTrue(nums: List<Int>): Boolean
    val sort: ConditionSort
}