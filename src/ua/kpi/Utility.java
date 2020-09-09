package ua.kpi;

import java.util.*;

public class Utility {

    final static Map<Integer, Double> tableData =
            initiasiseTableData();

    public static double getDispersion(List<Double> array) {
        double average = array.stream().mapToDouble(i -> i).sum() / array.size();
        return array.stream().mapToDouble(i -> Math.pow((i - average), 2)).sum() / (array.size() - 1);
    }

    public static double getAverage(List<Double> array) {
        return array.stream().mapToDouble(i -> i).sum() / array.size();
    }

    public static Map<List<Double>, Integer> getIntervals(List<Double> list) {
        Map<List<Double>, Integer> intervalMap = new TreeMap<>(new ListDoubleComparator());
        double difference = list.stream().max(Comparator.comparing(Double::doubleValue)).get() / 20;
        double first = list.get(0);
        double last = first + difference;
        intervalMap.put(Arrays.asList(first, last), getNumberInInterval(list, first, last));
        for (int i = 0; i < 20; i++) {
            first = last;
            last = first + difference;
            intervalMap.put(Arrays.asList(first, last), getNumberInInterval(list, first, last));
        }

        return checkIntervals(intervalMap);
    }

    private static Map<List<Double>, Integer> checkIntervals(Map<List<Double>, Integer> intervals) {
        Map<List<Double>, Integer> temp = new TreeMap<>( new ListDoubleComparator());
        List<List<Double>> keys = new ArrayList<>(intervals.keySet());
        List<Integer> values = new ArrayList<>(intervals.values());
        for (int i = 0; i < intervals.size(); i++) {
            if ((new ArrayList<>(intervals.values()).get(i) <= 5)) {
               if(i+1<intervals.size()) {
                   temp.put(Arrays.asList(keys.get(i).get(0),keys.get(i+1).get(1)), values.get(i) + values.get(i+1));
                   i++;
               }
            }else temp.put(keys.get(i), values.get(i));

        }
        return temp;
    }

    private static int getNumberInInterval(List<Double> list, double begin, double end) {
        return (int) list.stream().filter(item -> item >= begin && item <= end).count();
    }

    public static void printCharacteristics(List<Double> resultList) {
        System.out.println("average: " + Utility.getAverage(resultList));
        System.out.println("dispersion: " + Utility.getDispersion(resultList));
    }

    static Map<Integer, Double> initiasiseTableData() {
        Map<Integer, Double> tableData = new HashMap<>();
        tableData.put(1, 3.8);
        tableData.put(2, 6.0);
        tableData.put(3, 7.8);
        tableData.put(4, 9.5);
        tableData.put(5, 11.1);
        tableData.put(6, 12.6);
        tableData.put(7, 14.1);
        tableData.put(8, 15.5);
        tableData.put(9, 16.9);
        tableData.put(10, 18.3);
        tableData.put(11, 19.7);
        tableData.put(12, 21.0);
        tableData.put(13, 22.4);
        tableData.put(14, 23.7);
        tableData.put(15, 25.0);
        tableData.put(16, 26.3);
        tableData.put(17, 27.6);
        tableData.put(18, 28.9);
        tableData.put(19, 30.1);
        tableData.put(20, 31.4);
        tableData.put(21, 32.7);
        return tableData;
    }
}


