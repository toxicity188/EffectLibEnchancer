package kor.toxicity.effectlibenhancer.physics;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import kor.toxicity.effectlibenhancer.util.DoubleParser;
import kor.toxicity.effectlibenhancer.util.DoubleUtil;
import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public abstract class PhysicsEffect extends Effect {
    public int amount = 1;
    public double bounceAmplifier = 1;
    public int bounceAmount = 0;
    public int interval = 1;
    public int objectDuration = 20;
    public double gravity = 1;
    public boolean orientPitch = true;


    public String velocity = "0";
    public String startX = "0";
    public String startY = "0";
    public String startZ = "0";
    public String startPitch = "0";
    public String startYaw = "0";

    protected DoubleParser[] v, x, y, z, p, r;

    public PhysicsEffect(EffectManager effectManager) {
        super(effectManager);
    }

    @Override
    public void onRun() {
        if (x == null) {
            v = DoubleUtil.getInstance().parseList(velocity);
            x = DoubleUtil.getInstance().parseList(startX);
            y = DoubleUtil.getInstance().parseList(startY);
            z = DoubleUtil.getInstance().parseList(startZ);
            p = DoubleUtil.getInstance().parseList(startPitch);
            r = DoubleUtil.getInstance().parseList(startYaw);
        }
        IntStream.range(0,amount).forEach(i -> {
            Location loc = getLocation().clone();

            loc.add(0, rand(y), 0);
            if (orientPitch) loc.setPitch(0);

            double t = -1 * loc.getYaw() / 180 * Math.PI;
            loc.setPitch(loc.getPitch() - (float) rand(p));
            loc.setYaw(loc.getYaw() + (float) rand(r));
            loc.add(rand(x) * Math.sin(t) - rand(z) * Math.sin(t + Math.PI / 2), rand(y), rand(x) * Math.cos(t) - rand(z) * Math.cos(t + Math.PI / 2));
            access(loc);
        });
    }
    public abstract void access(Location loc);
    public abstract void show(Location location);

    protected double rand(DoubleParser[] t) {
        return (t.length > 1) ? t[ThreadLocalRandom.current().nextInt(0,t.length)].get() : t[0].get();
    }

}
