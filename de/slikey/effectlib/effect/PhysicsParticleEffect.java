package de.slikey.effectlib.effect;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import kor.toxicity.effectlibenhancer.EffectLibEnhancer;
import kor.toxicity.effectlibenhancer.physics.PhysicsEffect;
import kor.toxicity.effectlibenhancer.physics.PhysicsObject;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class PhysicsParticleEffect extends PhysicsEffect {

    public Particle particle = Particle.REDSTONE;
    public int particles = 1;
    public int count = 1;
    public double horizSpread = 0;
    public double vertSpread= 0;

    private Random rand = ThreadLocalRandom.current();


    public PhysicsParticleEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        duration = 0;
        period = 1;
        iterations = 100;
    }
    @Override
    public void access(Location loc) {
        new ParticleRun(loc, rand(v), this);
    }
    @Override
    public void show(Location location) {
        Location loc;
        if (horizSpread != 0 && vertSpread != 0) {
            loc = location.clone();
            loc.add(nextDouble(horizSpread),nextDouble(vertSpread),nextDouble(horizSpread));
        } else loc = location;
        display(particle,loc,color,speed,count);
    }
    private double nextDouble(double d) {
        return 2 * (rand.nextDouble() - 0.5) * d;
    }


    private static class ParticleRun implements Runnable {

        private final PhysicsParticleEffect e;

        private int loop = 0;
        private final PhysicsObject physics;
        private final BukkitTask task;

        private ParticleRun(Location location, double v, PhysicsParticleEffect e) {
            this.e = e;
            physics = new PhysicsObject(location, v, 1D / (double) e.particles * e.interval, e.gravity, e.bounceAmplifier, e.bounceAmount);
            task = EffectLibEnhancer.runTaskTimer(this, 0, e.interval);
        }


        @Override
        public void run() {
            loop++;
            IntStream.range(0, e.particles).forEach(i -> {
                physics.update();
                e.show(physics.getLocation());
            });
            if (loop >= e.objectDuration) {
                task.cancel();
            }
        }
    }
}
