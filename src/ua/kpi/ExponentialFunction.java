package ua.kpi;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class ExponentialFunction implements UnivariateFunction {

    private final double average;

    public ExponentialFunction(double average) {
        this.average = average;
    }

    @Override
    public double value(double x) {
        return 1- Math.pow(Math.exp(1), -average*x);
    }
}
