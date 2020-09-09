package ua.kpi;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Random1 random1 = new Random1();
        Random2 random2 = new Random2();
        Random3 random3 = new Random3();

        random1.analyse();
        random2.analyse();
        random3.analyse();
    }

}
