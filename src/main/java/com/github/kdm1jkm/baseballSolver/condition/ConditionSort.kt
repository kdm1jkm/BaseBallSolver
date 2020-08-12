package com.github.kdm1jkm.baseballSolver.condition

import com.github.kdm1jkm.baseballSolver.condition.singleCondition.BallCondition
import com.github.kdm1jkm.baseballSolver.condition.singleCondition.OutCondition
import com.github.kdm1jkm.baseballSolver.condition.singleCondition.SingleCondition
import com.github.kdm1jkm.baseballSolver.condition.singleCondition.StrikeCondition

enum class ConditionSort(private val conditionClass: Class<out SingleCondition>) {
    Strike(StrikeCondition::class.java), Ball(BallCondition::class.java), Out(OutCondition::class.java);

    fun getInstance(num: Int, pos: Int): SingleCondition {
        return conditionClass.getConstructor(
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
        ).newInstance(num, pos)
    }

    companion object {
        @JvmStatic
        fun getList(counts: Map<ConditionSort, Int>): List<ConditionSort> {
            val result: MutableList<ConditionSort> = ArrayList()
            counts.forEach {
                for (i in 0 until it.value) {
                    result.add(it.key)
                }
            }
            return result
        }
    }

}