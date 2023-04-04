package com.jzh;

import java.io.Serializable;

/**
 * 信号分析结果
 *
 * @author akyna
 * @date 04/04 004 6:02 PM
 */
public class ResOfSignalAnalysis implements Serializable {
    private double min;
    private double max;
    private double avg;
    private double var;

    public ResOfSignalAnalysis(double min, double max, double avg, double var) {
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.var = var;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "ResOfSignalAnalysis{" +
                "min=" + min +
                ", max=" + max +
                ", avg=" + avg +
                ", var=" + var +
                '}';
    }

    public double getVar() {
        return var;
    }

    public void setVar(double var) {
        this.var = var;
    }
}
