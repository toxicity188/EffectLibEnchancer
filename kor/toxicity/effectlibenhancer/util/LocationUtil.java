package kor.toxicity.effectlibenhancer.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationUtil extends MathUtil {

    private static final LocationUtil instance = new LocationUtil();

    public static LocationUtil getInstance() {
        return instance;
    }
    private LocationUtil() {

    }

    public void rotateYaw(Vector target, double yaw) {
        double x = -target.getX();
        double z = target.getZ();
        double t = toRadians(yaw);
        target.setX(- z*sin(t) - x*sin(t+HPI)).setZ(z*cos(t) + x*cos(t+HPI));
    }

    public void rotatePitch(Vector target, double pitch) {
        double x = target.getX();
        double y = target.getY();
        double t = toRadians(pitch);
        target.setY(x*sin(t) + y*sin(t+HPI)).setX(x*cos(t) + y*cos(t+HPI));
    }

    public void rotateRoll(Vector target, double roll) {
        double z = target.getZ();
        double y = target.getY();
        double t = toRadians(roll);
        target.setY(z*sin(t) + y*sin(t+HPI)).setZ(z*cos(t) + y*cos(t+HPI));
    }

    public void rotate(Vector target, double pitch, double yaw, double roll) {
        rotateRoll(target,roll);
        rotateYaw(target,yaw);
        rotatePitch(target,pitch);
    }
}
