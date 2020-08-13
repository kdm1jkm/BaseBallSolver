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
        runUserGame(4)
    }

    private fun runUserGame(length: Int) {
        val scanner = Scanner(System.`in`)

        val baseballGame = BaseballGame(length)

        while (!baseballGame.isClear) {
            if (Debug.showDebug) {
                printPossibleCases(baseballGame)
            }

            val nums = baseballGame.bestQuestion
            println(nums)
            print("Enter strike: ")
            val s = scanner.nextInt()
            print("Enter ball: ")
            val b = scanner.nextInt()

            baseballGame.addRecord(Record(nums, s, b))
        }
        println("Answer: ${baseballGame.possibleCases[0]}")
    }

    private fun printPossibleCases(baseballGame: BaseballGame) {
        for (case in baseballGame.possibleCases) {
            for (n in case) {
                System.out.printf("%d ", n)
            }
            println("")
        }
    }

    private fun runTestGame(length: Int, loop: Int) {
        val result = ArrayList<Int>()
        for (i in 0 until loop) {
            print("Run #${i + 1} ")
            val nums = BaseballGame.NUMBERS.shuffled().subList(0, length)
            result.add(test(nums))
            println("count: ${result.last()}")
        }
        result.sort()
        println("min: ${result[0]}")
        println("max: ${result.last()}")
        println("avg: ${result.average()}")
    }

    private fun test(nums: List<Int>): Int {
        val baseballGame = BaseballGame(nums.size)
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
}