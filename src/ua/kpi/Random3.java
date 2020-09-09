package ua.kpi;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Random3 {

    private double min = 0;
    private double max = 0;


    public List<Double> random() {
        double a = Math.pow(5, 13);
        double c = Math.pow(2, 31);

        List<Double> resultList = new LinkedList<>();
        double z =  a * Math.random() % c;
        for (int i = 0; i < 1000; i++) {

            z = a * z % c;
            resultList.add(Math.ceil((z / c) * 100) / 100);
//            System.out.printf("%5.2f%n", (z / c));
        }

        min = resultList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        max = resultList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();

        Utility.printCharacteristics(resultList);

        return resultList.stream().sorted().collect(Collectors.toList());
    }

    public List<Double> getExpectedValues(Map<List<Double>, Integer> intervals) {
        List<List<Double>> intervalList = new ArrayList<>(intervals.keySet());
        List<Double> expectedList = new LinkedList<>();

        for (int i = 0; i < intervalList.size(); i++) {
            expectedList.add(integrateExp(intervalList, i));
        }
        return expectedList;
    }

    private double integrateExp(List<List<Double>> intervalList, int i) {
        return ((intervalList.get(i).get(1) - intervalList.get(i).get(0)) / (max - min));
    }



    void analyse() {
        Utility.printName(3);
        List<Double> inputList = random();

        Map<List<Double>, Integer> intervals = Utility.getIntervals(inputList);
        List<Double> expectedList = getExpectedValues(intervals);
        List<Integer> observedList = intervals.values().stream().map(Integer::new).collect(Collectors.toList());

        double observedXiSquare = Utility.xiSquare(intervals.size(), expectedList, observedList);
        System.out.print("ObservedXiSquare = " + observedXiSquare + ";");
        double expectedXiSquare = Utility.tableData.get(intervals.size() - 1);
        System.out.println(" ExpectedXiSquare = " + expectedXiSquare);
        System.out.println("result:" + (observedXiSquare < expectedXiSquare));

    }

}
