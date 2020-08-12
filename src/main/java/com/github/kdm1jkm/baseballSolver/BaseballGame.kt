package com.github.kdm1jkm.baseballSolver

import com.github.kdm1jkm.baseballSolver.debug.Debug
import com.github.kdm1jkm.baseballSolver.record.Record
import com.github.kdm1jkm.baseballSolver.util.Util
import kotlin.math.abs

class BaseballGame(val length: Int) {
    companion object {
        private val NUMBERS = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    private val records: MutableList<Record> = ArrayList()

    var possibleCases = Util.getPermutations(NUMBERS, length)
        private set

    fun addRecord(record: Record) {
        records.add(record)
        possibleCases = possibleCases.filter { element ->
            record.isTrue(element.toIntArray())
        }
    }

    fun getBestQuestion() {
        // 남은 경우의 수에 있는 숫자들의 빈도수 세기
        val possibleCaseFrequency = countFrequency()

        // 빈도수가 전체 숫자의 50%에 가까울수록 작은 숫자가 나옴
        possibleCaseFrequency.forEach { (t, u) ->
            val new = abs(u * 100 / possibleCases.size - 50)
            possibleCaseFrequency[t] = new
            if (Debug.showDebug)
                println("$t: $u -> $new")
        }
        val sorted = possibleCaseFrequency.toList().sortedWith(compareBy { it.second })
        val frequentNums = ArrayList<Int>(length)
        for (i in 0 until length) {
            frequentNums.add(sorted[i].first)
        }

        // 빈도수가 50%에 가까운 length개의 숫자들의 위치 빈도수를 셈
        val positionFrequency = HashMap<Int, HashMap<Int, Int>>()
        frequentNums.forEach {
            positionFrequency[it] = HashMap()
            for (i in 0 until length) {
                positionFrequency[it]!![i] = 0
            }
        }
        for (possibleCase in possibleCases) {
            for ((i, value) in possibleCase.withIndex()) {
                if (frequentNums.contains(value)) {
                    positionFrequency[value]!![i] = positionFrequency[value]!![i]!! + 1
                }
            }
        }

        if (Debug.showDebug) {
            println("frequentNums = $frequentNums")
            println("positionFrequency = $positionFrequency")
        }
    }

    private fun weighFrequency(): HashMap<Int, Int> {
        val result = HashMap<Int, Int>()

        for (num in NUMBERS) {
            result[num] = 0
        }
        for (record in records) {
            val weight = record.ball + record.strike
            for (num in record.nums) {
                result[num] = result[num]!! + weight
            }
        }
        return result
    }

    private fun countFrequency(): HashMap<Int, Int> {
        val result = HashMap<Int, Int>()
        NUMBERS.forEach { num ->
            result[num] = 0
        }
        for (possibleCase in possibleCases) {
            possibleCase.forEach {
                result[it] = result[it]!! + 1
            }
        }
        return result
    }

}