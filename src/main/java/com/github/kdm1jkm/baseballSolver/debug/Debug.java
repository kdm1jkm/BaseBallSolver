package com.github.kdm1jkm.baseballSolver.debug;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    public static boolean showDebug = false;
    public static final List<String> log = new ArrayList<>();
    public static void printLog(){
        log.forEach(System.out::println);
    }
}
