package com.github.kdm1jkm.baseballSolver.condition;

import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.BallCondition;
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.OutCondition;
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.SingleCondition;
import com.github.kdm1jkm.baseballSolver.condition.SingleCondition.StrikeCondition;
import com.github.kdm1jkm.baseballSolver.debug.Debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Condition {
    private final List<SingleCondition> conditionList = new ArrayList<>();

    public SingleCondition[] getConditions() {
        return conditionList.toArray(new SingleCondition[0]);
    }

    public void addCondition(SingleCondition condition) {
        conditionList.add(condition);
    }

    public boolean isTrue(int[] nums) {
        if (Debug.showDebug){
            conditionList.forEach(singleCondition -> {
                if (singleCondition instanceof StrikeCondition) {
                    System.out.printf("s %d %d /", singleCondition.getNum(), ((StrikeCondition) singleCondition).getPos());
                } else if (singleCondition instanceof BallCondition) {
                    System.out.printf("b %d %d /", singleCondition.getNum(), ((BallCondition) singleCondition).getPos());
                } else if (singleCondition instanceof OutCondition) {
                    System.out.printf("o %d /", singleCondition.getNum());
                }
                else{
                    System.out.print("What is this /");
                }
            });}
        for (SingleCondition condition : conditionList) {
            if (!condition.isTrue(nums)) return false;
        }
        return true;
    }

    public int[] getNums() {
        int[] result = new int[conditionList.size()];
        for (int i = 0; i < conditionList.size(); i++) {
            result[i] = conditionList.get(i).getNum();
        }
        Arrays.sort(result);
        return result;
    }

    public Condition copy() {
        Condition result = new Condition();
        conditionList.forEach(condition -> result.addCondition(condition.copy()));
        return result;
    }

}
