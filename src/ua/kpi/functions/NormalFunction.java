package ua.kpi.functions;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class NormalFunction implements UnivariateFunction {

    private final int a;
    private final int mu;

    public NormalFunction(int a, int mu) {
        this.a = a;
        this.mu = mu;
    }

    @Override
    public double value(double x) {
        return (Math.exp(-Math.pow(x-a,2)/(2*Math.pow(mu,2))))/(mu * Math.sqrt(2*Math.PI));
    }
}
