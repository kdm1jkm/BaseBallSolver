package com.github.kdm1jkm.baseballSolver.main;

import com.github.kdm1jkm.baseballSolver.BaseballGame;
import com.github.kdm1jkm.baseballSolver.debug.Debug;
import com.github.kdm1jkm.baseballSolver.record.Record;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Debug.showDebug = true;
        BaseballGame baseballGame = new BaseballGame(3);

        Scanner scanner = new Scanner(System.in);

        while (!baseballGame.isSolved()) {
            for (int[] _case : baseballGame.possibleCase) {
                for (int n : _case) {
                    System.out.printf("%d ", n);
                }
                System.out.println("");
            }

            System.out.print("Enter number: ");
            int num = scanner.nextInt();
            System.out.print("Enter strike: ");
            int s = scanner.nextInt();
            System.out.print("Enter ball: ");
            int b = scanner.nextInt();

            int[] nums = new int[3];
            nums[2] = num % 10;
            nums[1] = (num / 10) % 10;
            nums[0] = (num / 100);

            baseballGame.apply(nums, s, b);

        }
    }
}
