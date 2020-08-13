package com.github.kdm1jkm.baseballSolver.main

import com.github.kdm1jkm.baseballSolver.BaseballGame
import com.github.kdm1jkm.baseballSolver.debug.Debug
import com.github.kdm1jkm.baseballSolver.record.Record
import java.util.*

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Debug.showDebug = true
        val scanner = Scanner(System.`in`)

        val baseballGame = BaseballGame(3)

//        baseballGame.addRecord(Record(intArrayOf(1, 2, 3), 1, 1))
//        baseballGame.addRecord(Record(intArrayOf(4, 5, 6), 0, 0))
//        baseballGame.addRecord(Record(intArrayOf(7, 8, 9), 0, 1))
//        baseballGame.addRecord(Record(intArrayOf(3, 8, 2), 0, 2))
//        baseballGame.getBestQuestion()

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

            val nums = IntArray(3)
            nums[2] = num % 10
            nums[1] = num / 10 % 10
            nums[0] = num / 10 / 10 % 10
//            nums[0] = num / 10 / 10 / 10 % 10

            baseballGame.addRecord(Record(nums, s, b))
            baseballGame.getBestQuestion()
        }
    }
}