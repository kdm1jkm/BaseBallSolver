package com.github.kdm1jkm.baseballSolver.main

import com.github.kdm1jkm.baseballSolver.BaseballGame
import com.github.kdm1jkm.baseballSolver.GameHost
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.Ball
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.Strike
import com.github.kdm1jkm.baseballSolver.debug.Debug
import com.github.kdm1jkm.baseballSolver.record.Record
import java.util.*
import kotlin.collections.ArrayList

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Debug.showDebug = false
        val length = 9
        val result = ArrayList<Int>()
        for (i in 1..50) {
            print("Run #$i ")
            result.add(runGame(length))
            println("count: ${result.last()}")
        }
        result.sort()
        println("min: ${result[0]}")
        println("max: ${result.last()}")
        println("avg: ${result.average()}")
    }

    private fun runGame(length: Int): Int {
        val baseballGame = BaseballGame(length)
        val nums = BaseballGame.NUMBERS.shuffled().subList(0, length)
        val host = GameHost(nums)
        if (Debug.showDebug)
            println("answer: $nums")
        var count = 0
        while (!baseballGame.isClear) {
            val question = baseballGame.bestQuestion
            val answer = host.getScore(question)
            baseballGame.addRecord(Record(question, answer[Strike]!!, answer[Ball]!!))
            if (Debug.showDebug)
                println("question: $question, answer: $answer, possibleCase: ${baseballGame.possibleCases.size}")
            count++
        }
        assert(baseballGame.possibleCases[0] == nums)
        return count
    }

    private fun userInputMain() {
        Debug.showDebug = true
        val scanner = Scanner(System.`in`)

        val baseballGame = BaseballGame(3)

        baseballGame.addRecord(Record(listOf(1, 2, 3), 1, 0))
        baseballGame.addRecord(Record(listOf(4, 5, 6), 0, 1))
        baseballGame.addRecord(Record(listOf(7, 8, 9), 0, 1))

        while (true) {
            for (case in baseballGame.possibleCases) {
                for (n in case) {
                    System.out.printf("%d ", n)
                }
                println("")
            }

            print("Enter number: ")
            val num = scanner.nextInt()
            print("Enter strike: ")
            val s = scanner.nextInt()
            print("Enter ball: ")
            val b = scanner.nextInt()

            val nums = ArrayList<Int>(3)
            nums.add(num / 10 / 10 % 10)
            nums.add(num / 10 % 10)
            nums.add(num % 10)

            baseballGame.addRecord(Record(nums, s, b))
            baseballGame.bestQuestion
        }
    }
}