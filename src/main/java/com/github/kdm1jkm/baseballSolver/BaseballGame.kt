package com.github.kdm1jkm.baseballSolver

import com.github.kdm1jkm.baseballSolver.exception.NoSuchPossibilityException
import com.github.kdm1jkm.baseballSolver.record.Record
import com.github.kdm1jkm.baseballSolver.util.Util

class BaseballGame(val length: Int) {
    companion object {
        val NUMBERS = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    private val earlyNums = NUMBERS.shuffled().chunked(length).filter { it.size == length }
    private val records: MutableList<Record> = ArrayList()
    private fun checkLackOfRecords() = records.size < earlyNums.size

    var possibleCases = Util.getPermutations(NUMBERS, length)
        private set

    val bestQuestion: List<Int>
        get() {
            return when {
                checkLackOfRecords() -> earlyNums[records.size]
                else -> possibleCases.random()
            }
        }

    val isClear: Boolean
        get() = possibleCases.size == 1

    fun addRecord(record: Record) {
        records.add(record)
        possibleCases = possibleCases.filter { element ->
            record.isTrue(element)
        }
        if (possibleCases.isEmpty()) throw NoSuchPossibilityException()
    }
}