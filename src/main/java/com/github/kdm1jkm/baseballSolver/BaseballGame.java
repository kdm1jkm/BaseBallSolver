package com.github.kdm1jkm.baseballSolver;

import com.github.kdm1jkm.baseballSolver.record.Record;
import com.github.kdm1jkm.baseballSolver.record.Records;

import java.util.ArrayList;
import java.util.List;

public class BaseballGame {
    private static final int[] AVAILABLE_NUMBERS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    //    private static final int[] AVAILABLE_NUMBERS = new int[]{1, 2, 3, 4};
    public final int length;
    public final List<int[]> possibleCase;
    private final Records records = new Records();

    public BaseballGame(int length) {
        this.length = length;
        possibleCase = getAllCase(length);
    }

    private static List<int[]> getAllCase(int length) {
        List<int[]> result = new ArrayList<>();

        if (length == 1) {
            for (int num : AVAILABLE_NUMBERS) {
                result.add(new int[]{num});
            }
            return result;
        }

        List<int[]> preResults = getAllCase(length - 1);
        for (int num : AVAILABLE_NUMBERS) {
            for (int[] preResult : preResults) {
                {
                    boolean isCollide = false;
                    for (int n : preResult) {
                        if (n == num) {
                            isCollide = true;
                            break;
                        }
                    }
                    if (isCollide) continue;
                }

                int[] arr = new int[length];
                arr[0] = num;
                System.arraycopy(preResult, 0, arr, 1, preResult.length);
                result.add(arr);
            }
        }

        return result;
    }

    public void apply(int[] nums, int strike, int ball) {
        Record record = new Record(nums, strike,ball);
        records.add(record);
        possibleCase.removeIf(_case -> !record.isTrue(_case));
    }

//    public int[] getAnswer() throws Exception;

    public boolean isSolved() {
        return possibleCase.size() == 1;
    }

    public int[] getNumsToAsk() {
        return new int[0];
    }
}
