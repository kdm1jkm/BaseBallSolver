package com.github.kdm1jkm.baseballSolver.condition

import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.SingleCondition
import java.util.*

class Condition : Cloneable {
    private val data: MutableSet<SingleCondition> = HashSet()

    //    private final List<SingleCondition> data = new ArrayList<>();
    fun addCondition(condition: SingleCondition) {
        data.add(condition)
    }

    fun isTrue(nums: IntArray): Boolean {
        // 하나라도 거짓이면 거짓
        for (condition in data) if (!condition.isTrue(nums)) return false
        return true
    }

    val conditions: Array<SingleCondition>
        get() = data.toTypedArray()

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        val clone = super.clone() as Condition
        clone.data.addAll(data)
        return clone
    }
}