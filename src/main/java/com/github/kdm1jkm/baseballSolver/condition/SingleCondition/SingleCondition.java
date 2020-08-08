package com.github.kdm1jkm.baseballSolver.condition.SingleCondition;

public interface SingleCondition {
    boolean isTrue(int[] nums);
    int getNum();
    SingleCondition copy();
}
