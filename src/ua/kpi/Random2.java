package ua.kpi;

import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;

import java.util.*;
import java.util.stream.Collectors;

public class Random2 {

    private int mu = 20;
    private int a = 100;

    double getM() {
        double m = 0;
        for (int i = 0; i < 12; i++) {
            m += Math.random();
        }
        return m - 6;
    }

    public List<Double> random() {
        List<Double> resultList = new LinkedList<>();

        for (int i = 0; i < 1000; i++) {
            resultList.addAll(Collections.singleton((mu * getM() + a)));
            System.out.printf("%5.2f%n", (mu * getM() + a));
        }

        Utility.printCharacteristics(resultList);

        return resultList.stream().sorted().collect(Collectors.toList());
    }

    public List<Double> getExpectedValues(Map<List<Double>,Integer> intervals) {
        List<List<Double>> intervalList = new ArrayList<>(intervals.keySet());
        List<Double> expectedList =  new LinkedList<>();

        for (int i = 0; i < intervalList.size() ; i++) {
            expectedList.add(integrateNormal(intervalList,i));
        }
        return expectedList;
    }

    public double integrateNormal(List<List<Double>> intervalList , int i) {
        NormalFunction function = new NormalFunction(a, mu);
        SimpsonIntegrator integrator = new SimpsonIntegrator();
        return integrator.integrate(200, function,intervalList.get(i).get(0),intervalList.get(i).get(1));
    }

    double xiSquare(int numberOfIntervals, List<Double> expectedList, List<Integer> frequencyList) {
        double xi2=0;
        for (int i = 0; i <numberOfIntervals ; i++) {
            xi2+=Math.pow(frequencyList.get(i)-1000*expectedList.get(i),2)/(1000*expectedList.get(i));
        }
        return xi2;
    }

    void analyse() {
        List<Double> inputList = random();

        Map<List<Double>,Integer> intervals = Utility.getIntervals(inputList);

        List<Double> expectedList = getExpectedValues(intervals);
        List<Integer> observedList = intervals.values().stream().map(Integer::new).collect(Collectors.toList());

        double observedXiSquare = xiSquare(intervals.size(),expectedList,observedList);
        System.out.print(" ObservedXiSquare = " +observedXiSquare + ";");
        double expectedXiSquare = Utility.tableData.get(intervals.size()-1);
        System.out.println(" ExpectedXiSquare = " +expectedXiSquare);
        System.out.println("result:" +(observedXiSquare<expectedXiSquare));

    }
}
