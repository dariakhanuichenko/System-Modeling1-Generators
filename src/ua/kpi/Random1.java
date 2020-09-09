package ua.kpi;

import ua.kpi.functions.ExponentialFunction;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Random1 {

    private double average;

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public List<Double> random() {
        List<Double> resultList = new LinkedList<>();
        double psi = 0;
        int lyamda = 1;
        for (int i = 0; i < 1000; i++) {
            psi = Math.random();
            resultList.addAll(Collections.singleton((-Math.log(psi) / lyamda)));
//            System.out.printf("%5.2f%n", (-Math.log(psi) / lyamda));
        }
        this.average = Utility.getAverage(resultList);

        Utility.printCharacteristics(resultList);

        return resultList.stream().sorted().collect(Collectors.toList());
    }

    public List<Double> getExpectedValues(Map<List<Double>, Integer> intervals, double average) {
        List<List<Double>> intervalList = new ArrayList<>(intervals.keySet());
        List<Double> expectedList = new LinkedList<>();
        for (int i = 0; i < intervalList.size(); i++) {
            expectedList.add(integrateExp(average, intervalList, i));
        }
        return expectedList;
    }

    public double integrateExp(double average, List<List<Double>> intervalList, int i) {
        ExponentialFunction exponentialFunction = new ExponentialFunction(average);
        return exponentialFunction.value(intervalList.get(i).get(1)) - exponentialFunction.value(intervalList.get(i).get(0));
    }


    void analyse() {
        Utility.printName(1);

        List<Double> inputList = random();

        Map<List<Double>, Integer> intervals = Utility.getIntervals(inputList);

        List<Double> expectedList = getExpectedValues(intervals, average);
        List<Integer> observedList = intervals.values().stream().map(Integer::new).collect(Collectors.toList());

        try{
        ExportToExcel.exportReportByLoadGeneralToExcel(intervals,1);}
        catch(IOException e){
            System.out.println("File (Generator1) wasn't written");
        }
        double observedXiSquare = Utility.xiSquare(intervals.size(), expectedList, observedList);
        System.out.print("ObservedXiSquare = " + observedXiSquare + ";");
        double expectedXiSquare = Utility.tableData.get(intervals.size() - 1);
        System.out.println(" ExpectedXiSquare = " + expectedXiSquare);
        System.out.println("result:" + (observedXiSquare < expectedXiSquare) +"\n");

    }

}
