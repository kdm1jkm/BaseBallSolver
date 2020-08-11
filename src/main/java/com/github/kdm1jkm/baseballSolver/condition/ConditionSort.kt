package com.github.kdm1jkm.baseballSolver.condition

import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.BallCondition
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.OutCondition
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.SingleCondition
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.StrikeCondition
import java.lang.reflect.InvocationTargetException
import java.util.*

enum class ConditionSort(private val conditionClass: Class<out SingleCondition>) {
    Strike(StrikeCondition::class.java), Ball(BallCondition::class.java), Out(OutCondition::class.java);

    @Throws(NoSuchMethodException::class, IllegalAccessException::class, InvocationTargetException::class, InstantiationException::class)
    fun getInstance(num: Int, pos: Int): SingleCondition {
        return if (this == Out) {
            conditionClass.getConstructor(Int::class.javaPrimitiveType).newInstance(num)
        } else {
            conditionClass.getConstructor(Int::class.javaPrimitiveType, Int::class.javaPrimitiveType).newInstance(num, pos)
        }
    }

    companion object {
        @JvmStatic
        fun getList(counts: Map<ConditionSort, Int>): List<ConditionSort> {
            val result: MutableList<ConditionSort> = ArrayList()
            counts.forEach { (conditionSort, integer) ->
                for (i in 0 until integer) {
                    result.add(conditionSort)
                }
            }
            return result
        }
    }

}