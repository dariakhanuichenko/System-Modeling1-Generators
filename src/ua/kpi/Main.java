package ua.kpi;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        ChiSquareTest test = new ChiSquareTest() ;
        Random1 random1 = new Random1();
        Random2 random2 = new Random2();
        Random3 random3 = new Random3();
//        random1.analyse();
//        random2.analyse();
        random3.analyse();
//        System.out.println("--------------");
//        random2.random();
//        System.out.println("--------------");
//        random3.random();
//        final long[] list1 = new long[]{1,2,3,4,5,4,3,2,1,3};
//        final double[] list2= new  double[]{1,2,3,4,5,4,3,2,1,3};
//        final double n = 0.05;
//        System.out.println(test.chiSquareTest( list2, list1, 0.1));
    }

}
