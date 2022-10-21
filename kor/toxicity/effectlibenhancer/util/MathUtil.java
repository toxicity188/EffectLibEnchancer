package kor.toxicity.effectlibenhancer.util;

public abstract class MathUtil {

    protected final double HPI = Math.PI/2;

    protected double cos(double d) {
        return Math.cos(d);
    }
    protected double sin(double d) {
        return Math.sin(d);
    }

    protected double sCos(double d) {
        return (d > 0) ? cos(d) : -cos(abs(d));
    }
    protected double sSin(double d) {
        return (d > 0) ? sin(d) : -sin(abs(d));
    }

    protected double toRadians(double d) {
        return Math.toRadians(d);
    }
    protected double sqrt(double d) {
        return Math.sqrt(d);
    }
    protected double length(double x, double z) {
        return sqrt(pow(x,2) + pow(z,2));
    }
    protected double pow(double d, double a) {
        return Math.pow(d,a);
    }
    protected double abs(double d) {
        return Math.abs(d);
    }
}
