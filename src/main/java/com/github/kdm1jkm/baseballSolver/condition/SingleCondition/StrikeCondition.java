package com.github.kdm1jkm.baseballSolver.condition.SingleCondition;

public class StrikeCondition implements SingleCondition {
    private final int num;
    private final int pos;

    public StrikeCondition(int num, int pos) {
        this.num = num;
        this.pos = pos;
    }

    public boolean isTrue(int[] nums) {
        if (nums.length <= pos) return false;
        return nums[pos] == num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public SingleCondition copy() {
        return new StrikeCondition(num, pos);
    }

    public int getPos() {
        return pos;
    }
}
