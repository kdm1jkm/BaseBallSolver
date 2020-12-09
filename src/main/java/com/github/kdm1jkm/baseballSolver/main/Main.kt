package com.github.kdm1jkm.baseballSolver.main

import com.github.kdm1jkm.baseballSolver.BaseballGame
import com.github.kdm1jkm.baseballSolver.GameHost
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.Ball
import com.github.kdm1jkm.baseballSolver.condition.ConditionSort.Strike
import com.github.kdm1jkm.baseballSolver.debug.Debug
import com.github.kdm1jkm.baseballSolver.record.Record
import org.apache.commons.cli.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
//        runUserGame(5)
        val options = Options()
        options.addOption(Option("u", "user", false, "Run a user game."))
        options.addOption(Option.builder("c")
                .longOpt("count")
                .argName("count")
                .hasArg()
                .desc("The number of loop for test, not user game(Default: 10)")
                .build())
        options.addOption(Option.builder("n")
                .longOpt("num")
                .argName("number")
                .hasArg()
                .desc("The length of baseball game(Difficulty)")
                .required()
                .build()
        )
        options.addOption(Option.builder("z")
                .longOpt("zero")
                .argName("Include zero")
                .build()
        )
        val formatter = HelpFormatter()
        try {
            runOnce(options, args)
        } catch (e: Exception) {
            println(e.message)
            println(e.stackTrace)
            formatter.printHelp("java -jar BaseBallSolver.jar", options)
        }
    }

    private fun runOnce(options: Options, args: Array<String>) {
        val parser: CommandLineParser = DefaultParser()
        val commandLine = parser.parse(options, args)
        val count = if (commandLine.hasOption("c"))
            Integer.parseInt(commandLine.getOptionValue("c")) else 10
        val num = Integer.parseInt(commandLine.getOptionValue("n"))

        if (commandLine.hasOption("z")) {
            BaseballGame.NUMBERS = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        } else {
            BaseballGame.NUMBERS = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        }

        if (commandLine.hasOption("u")) {
            runUserGame(num)
        } else {
            runTestGame(num, count)
        }
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
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        for (i in 0 until loop) {
            print("Run #${i + 1} ")
            val nums = BaseballGame.NUMBERS.shuffled().subList(0, length)
            result.add(test(nums))
            println("count: ${result.last()}")
            min = min(min, result.last())
            max = max(max, result.last())
        }
        println("min: $min")
        println("max: $max")
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