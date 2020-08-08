package com.github.kdm1jkm.baseballSolver.record;

import java.util.ArrayList;

public class Records extends ArrayList<Record> {

    public boolean isTrue(int[] nums) {
        for (Record record : this) {
            if (!record.isTrue(nums)) {
                return false;
            }
        }
        return true;
    }

}
