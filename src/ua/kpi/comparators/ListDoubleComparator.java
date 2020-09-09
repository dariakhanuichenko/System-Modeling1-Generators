package ua.kpi.comparators;

import java.util.Comparator;
import java.util.List;

public class ListDoubleComparator implements Comparator<List<Double>>
    {
        @Override
        public int compare(List<Double> o1, List<Double> o2) {
            return o1.get(0).compareTo(o2.get(0));
        }
    }
