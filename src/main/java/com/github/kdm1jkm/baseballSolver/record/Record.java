package com.github.kdm1jkm.baseballSolver.record;

import com.github.kdm1jkm.baseballSolver.condition.Condition;
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.BallCondition;
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.OutCondition;
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.SingleCondition;
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.StrikeCondition;
import com.github.kdm1jkm.baseballSolver.debug.Debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Record {
    private final int[] nums;
    private final int strike;
    private final int ball;
    private final List<Condition> conditions;
    private final int length;

    public Record(int[] nums, int strike, int ball) {
        this.nums = nums;
        this.strike = strike;
        this.ball = ball;
        length = nums.length;
        conditions = makeConditions(nums, strike, ball);
    }

    private static List<boolean[]> getCombinations(int length, int count) {
        if (count == 0) {
            boolean[] result = new boolean[length];
            Arrays.fill(result, false);
            return Collections.singletonList(result);
        }
        if (length == count) {
            boolean[] result = new boolean[length];
            Arrays.fill(result, true);
            return Collections.singletonList(result);
        }

        List<boolean[]> result = new ArrayList<>();

        {
            List<boolean[]> combinations = getCombinations(length - 1, count - 1);
            for (boolean[] combination : combinations) {
                boolean[] newArr = new boolean[length];
                newArr[0] = true;
                System.arraycopy(combination, 0, newArr, 1, length - 1);
                result.add(newArr);
            }
        }
        {
            List<boolean[]> combinations = getCombinations(length - 1, count);
            for (boolean[] combination : combinations) {
                boolean[] newArr = new boolean[length];
                newArr[0] = false;
                System.arraycopy(combination, 0, newArr, 1, length - 1);
                result.add(newArr);
            }
        }

        return result;
    }

    public static List<int[]> getCombinations(int[] nums, int count) {
        if (Debug.showDebug)
            System.out.println("nums = " + Arrays.toString(nums) + ", count = " + count);
        if (count == 0) return Collections.singletonList(new int[0]);
        if (nums.length == count) return Collections.singletonList(nums);

        List<int[]> result = new ArrayList<>();
        {
            List<int[]> back = getCombinations(Arrays.copyOfRange(nums, 1, nums.length), count - 1);
            for (int[] b : back) {
                int[] arr = new int[count];
                arr[0] = nums[0];
                System.arraycopy(b, 0, arr, 1, count - 1);
                result.add(arr);
            }
        }
        {
            List<int[]> back = getCombinations(Arrays.copyOfRange(nums, 1, nums.length), count);
            result.addAll(back);
        }
        return result;
    }

    public static List<int[]> getPermutation(int[] nums, int count) {

        if (count == 0) {
            return Collections.singletonList(new int[0]);
        }

        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int[] others = new int[nums.length - 1];
            // nums의 i번째 항목 빼고 others에 복사
            System.arraycopy(nums, 0, others, 0, i);
            System.arraycopy(nums, i + 1, others, i, others.length - i);
//            for (int j = 0; j < nums.length; j++) {
//                if (j < i) others[j] = nums[j];
//                else if (j > i) others[j - 1] = nums[j];
//            }
            List<int[]> otherResults = getPermutation(others, count - 1);
            for (int[] otherResult : otherResults) {
                int[] arr = new int[count];
                arr[0] = num;
                System.arraycopy(otherResult, 0, arr, 1, count - 1);
                result.add(arr);
            }
        }
        return result;
    }

    public int[] getNums() {
        return nums;
    }

    public int getStrike() {
        return strike;
    }

    public int getBall() {
        return ball;
    }

    public boolean isTrue(int[] nums) {
        System.out.printf("%s\n", Arrays.toString(nums));
        System.out.printf("%s %d %d\n", Arrays.toString(this.nums), strike, ball);
        for (Condition condition : conditions) {
            if (condition.isTrue(nums)) {
                System.out.println("True\n");
                return true;
            }
        }
        System.out.println("False\n");
        return false;
    }

    private List<Condition> makeConditions(int[] nums, int strike, int ball) {
        if (strike == 0 && ball == 0) {
            Condition condition = new Condition();
            for (int num : nums)
                condition.addCondition(new OutCondition(num));
            return Collections.singletonList(condition);
        }
        List<Condition> strikeResults = new ArrayList<>();
        {
            List<boolean[]> strikePositions = getCombinations(length, strike);
            for (boolean[] strikePosition : strikePositions) {
                Condition condition = new Condition();
                for (int i = 0; i < strikePosition.length; i++) {
                    if (strikePosition[i]) {
                        condition.addCondition(new StrikeCondition(nums[i], i));
                    }
                }
                strikeResults.add(condition);
            }
        }
        if (ball == 0) return strikeResults;
        List<Condition> result = new ArrayList<>();
        {
            for (Condition strikeResult : strikeResults) {
                int[] state = new int[length];
                int blank = length;
                Arrays.fill(state, -1);
                for (SingleCondition condition : strikeResult.getConditions()) {
                    if (condition instanceof StrikeCondition) {
                        StrikeCondition strikeCondition = (StrikeCondition) condition;
                        int pos = strikeCondition.getPos();
                        int num = strikeCondition.getNum();
                        state[pos] = num;
                        blank--;
                    }
                }
                if (blank == 0) {
                    result.add(strikeResult.copy());
                    break;
                }

                int[] blankPos = new int[blank];
                {
                    int j = 0;
                    for (int i = 0; i < state.length; i++) {
                        if (state[i] == -1) {
                            blankPos[j] = i;
                            j++;
                        }
                    }
                }


                int[] balls = new int[blank];
                for (int i = 0; i < blank; i++) {
                    balls[i] = nums[blankPos[i]];
                }

                List<int[]> ballCombinations = getPermutation(balls, ball);
                List<int[]> ballPoses = getCombinations(blankPos, ball);

                if(Debug.showDebug) {
                    System.out.println("Arrays.toString(state) = " + Arrays.toString(state));
                    System.out.println("Arrays.toString(blankPos) = " + Arrays.toString(blankPos));
                    System.out.println("Arrays.toString(balls) = " + Arrays.toString(balls));
                    System.out.println("Arrays.toString(ballCombinations.toArray(new int[0][])) = " + Arrays.deepToString(ballCombinations.toArray(new int[0][])));
                    System.out.println("Arrays.deepToString(ballPoses.toArray()new int[0][]) = " + Arrays.deepToString(ballPoses.toArray(new int[0][])));
                }

                for(int[] ballPos: ballPoses){
                    Condition condition = strikeResult.copy();
                    for (int pos : ballPos) {
                        condition.addCondition(new BallCondition(nums[pos], pos));
                    }
                    result.add(condition);
                }

                /*
                for (int[] ballCombination : ballCombinations) {

                    loop:
                    for (int[] ballPos : ballPoses) {

                        Condition condition = strikeResult.copy();

                        if(Debug.showDebug){
                            System.out.println("----------------");
                            System.out.println("Arrays.toString(ballCombination) = " + Arrays.toString(ballCombination));
                            System.out.println("Arrays.toString(ballPos) = " + Arrays.toString(ballPos));}
                        for (int i = 0; i < ballPos.length; i++) {
                            if (ballCombination[i] == nums[ballPos[i]]) {
                                if(Debug.showDebug) {
                                    System.out.println("i = " + i); System.out.println("continue");}
                                continue loop;
                            }
                            condition.addCondition(new BallCondition(ballCombination[i], ballPos[i]));
                        }

                        result.add(condition);
                    }
                }
                                 */

            }
        }
        return result;
    }
}
