package com.github.kdm1jkm.baseballSolver.condition.SingleCondition;

public class BallCondition implements SingleCondition {
    private final int num;
    private final int pos;

    public BallCondition(int num, int pos) {
        this.num = num;
        this.pos = pos;
    }

    public boolean isTrue(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == num && i != pos) return true;
        }
        return false;
    }

    public int getNum() {
        return num;
    }

    public int getPos(){
        return pos;
    }

    @Override
    public SingleCondition copy() {
        return new BallCondition(num, pos);
    }
}
