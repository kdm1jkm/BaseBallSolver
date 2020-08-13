package com.github.kdm1jkm.baseballSolver

import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.*
import com.github.kdm1jkm.baseballSolver.debug.Debug
import com.github.kdm1jkm.baseballSolver.record.Record
import com.github.kdm1jkm.baseballSolver.util.Util

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
        val weightFrequency = weighFrequency()

        val sorted = weightFrequency.toList().sortedWith(compareByDescending { it.second })
        val frequentNums = ArrayList<Int>(length)
        for (i in 0 until length) {
            frequentNums.add(sorted[i].first)
        }

        val frequentNumPermutation = Util.getPermutations(frequentNums, length)
        val best =
                frequentNumPermutation.map {
                    if (Debug.showDebug) {
                        println("it = ${it}")
                    }
                    var weight = 0
                    records.forEach { record ->
                        val score = Util.getBaseballGameScore(record.nums.asList(), it)
                        if (score[Out]!! == 0) return@forEach
                        weight += score[Strike]!! - score[Ball]!!
//                        weight += record.strike - record.ball
                        if (Debug.showDebug) {
                            println("weight changed weight: $weight | s: ${score[Strike]}, b: ${score[Ball]} " +
                                    "/ nums: ${record.nums.contentToString()}")
//                            println("weight changed weight: $weight / s: ${record.strike}, b: ${record.ball} / nums: ${record.nums}")
                        }
                    }
                    if (Debug.showDebug) {
                        println("weight = ${weight}")
                    }
                    Pair(it, weight)
                }.sortedWith(compareByDescending { it.second })[0].first
        println("best = ${best}")
    }

    private fun weighFrequency(): Map<Int, Double> {
        val result = HashMap<Int, Pair<Int, Int>>()

        for (num in NUMBERS) {
            result[num] = Pair(0, 0)
        }
        for (record in records) {
            val weight = record.ball + record.strike
            for (num in record.nums) {
                result[num] = Pair(result[num]!!.first + weight, result[num]!!.second + 1)
            }
        }

        return result.map {
            Pair(it.key, it.value.first.toDouble() / it.value.second.toDouble())
        }.toMap()
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