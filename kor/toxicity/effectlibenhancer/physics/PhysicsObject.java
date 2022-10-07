package kor.toxicity.effectlibenhancer.physics;

import org.bukkit.Location;
import org.bukkit.Material;

public class PhysicsObject {

    private final Location origin;

    private double interval;

    private double velocity;
    private double yaw;
    private double pitch;
    private double gravity;
    private double gravityAcceleration;

    private double bounceAmplifier;
    private double bounceAmount;
    private double degree;

    public PhysicsObject(Location location, double velocity, double interval, double gravity, double bounce1, double bounce2) {
        origin = location.clone();

        this.gravity = gravity;
        this.velocity = velocity;
        this.interval = interval;
        bounceAmplifier = bounce1;
        bounceAmount = bounce2;
        setYaw(-origin.getYaw());
        setPitch(-origin.getPitch());
    }

    public void setPitch(double d) {
        pitch = toRadian(d);
    }
    public void setYaw(double d) {
        yaw = toRadian(d);
    }
    public Location getLocation() {
        return origin;
    }
    public double getDegree() {
        return degree;
    }
    public void update() {
        double v = velocity / 20D * interval;
        double p = sqrt(cos(pitch));

        double x = sin(yaw) * v * p;
        double z = cos(yaw) * v * p;
        double y = sin(pitch) * v;

        degree = Math.atan((gravityAcceleration*interval - y)/(v * cos(pitch)));

        origin.add(x, y, z);
        gravityAcceleration += gravity / 20D * interval;
        origin.add(0,-gravityAcceleration * interval,0);
        if (origin.getBlock().getType() != Material.AIR && bounceAmount > 0) {
            while (origin.getBlock().getType() != Material.AIR) origin.add(0,0.25,0);
            pitch = Math.abs(degree);
            velocity = (gravityAcceleration - y/interval)*20*bounceAmplifier;
            gravityAcceleration = 0;
            bounceAmount --;
        }
    }

    private double toRadian(double d) {
        return d / 180 * Math.PI;
    }
    private double cos(double d) {
        return Math.cos(d);
    }
    private double sin(double d) {
        return Math.sin(d);
    }
    private double sqrt(double d) {
        return Math.sqrt(d);
    }


}
