package com.github.kdm1jkm.baseballSolver.condition.SingleCondition;

public class OutCondition implements SingleCondition{
    private final int num;

    public OutCondition(int num) {
        this.num = num;
    }


    @Override
    public boolean isTrue(int[] nums) {
        for(int n : nums){
            if(n ==num )return false;
        }
        return true;
    }

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public SingleCondition copy() {
        return new OutCondition(num);
    }
}
