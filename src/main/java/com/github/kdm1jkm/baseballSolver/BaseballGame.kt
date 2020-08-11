package com.github.kdm1jkm.baseballSolver

import com.github.kdm1jkm.baseballSolver.debug.Debug
import com.github.kdm1jkm.baseballSolver.record.Record
import com.github.kdm1jkm.baseballSolver.util.Util
import kotlin.math.abs

class BaseballGame(val length: Int) {
    var possibleCases = Util.getPermutations(NUMBERS, length)
        private set

    private val records: MutableList<Record> = ArrayList()

    companion object {
        private val NUMBERS = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    fun addRecord(record: Record) {
        records.add(record)
        possibleCases = possibleCases.filter { element ->
            record.isTrue(element.toIntArray())
        }
    }

    fun getBestQuestion() {
        // 남은 경우의 수에 있는 숫자들의 빈도수 세기
        val frequency = HashMap<Int, Int>()
        NUMBERS.forEach { num ->
            frequency[num] = 0
        }
        for (possibleCase in possibleCases) {
            possibleCase.forEach {
                frequency[it] = frequency[it]!! + 1
            }
        }

        // 빈도수가 전체 숫자의 50%에 가까울수록 작은 숫자가 나옴
        frequency.forEach { (t, u) ->
            val new = abs(u * 100 / possibleCases.size - 50)
            frequency[t] = new
            if (Debug.showDebug)
                println("$t: $u -> $new")
        }
        val sorted = frequency.toList().sortedWith(compareBy { it.second })
        val frequentNums = ArrayList<Int>(length)
        for (i in 0 until length) {
            frequentNums.add(sorted[i].first)
        }

        // 빈도수가 50%에 가까운 length개의 숫자들의 위치 빈도수를 셈
        val positionFrequency = HashMap<Int, HashMap<Int, Int>>()
        frequentNums.forEach {
            positionFrequency[it] = HashMap()
            for(i in 0 until length){
                positionFrequency[it]!![i] = 0
            }
        }
        for(possibleCase in possibleCases){
            for((i, value) in possibleCase.withIndex()){
                if(frequentNums.contains(value)){
                    positionFrequency[value]!![i] = positionFrequency[value]!![i]!! + 1
                }
            }
        }

        println("frequentNums = $frequentNums")
        println("positionFrequency = $positionFrequency")
    }
}