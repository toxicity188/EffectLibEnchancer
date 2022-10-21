package de.slikey.effectlib.effect;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.math.EquationStore;
import de.slikey.effectlib.math.EquationTransform;
import kor.toxicity.effectlibenhancer.util.LocationUtil;
import org.bukkit.Particle;
import de.slikey.effectlib.util.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class EnhancedEquationEffect extends Effect {

    /**
     * This effect can be an easy version of EquationEffect.
     * Added some parameters: pitch, yaw, roll.
     * Added some constants:
     * PI : 3.14156295...
     * 2PI : PI x 2
     * R : random number between 0 and 1.
     * But this effect is slower than EquationEffect. maybe causes some server lag when abused.
     */
    private static final Map<String,Double> c = new LinkedHashMap<>();
    static {
        c.put("PI",Math.PI);
        c.put("2PI",Math.PI * 2);
    }
    public final Map<String,Double> constants = new LinkedHashMap<>(c);

    public Particle particle = Particle.REDSTONE;
    public String xEquation = "t";
    public String yEquation = "0";
    public String zEquation = "0";
    public int particles = 1;
    public String x2Equation = null;
    public String y2Equation = null;
    public String z2Equation = null;
    public int particles2 = 0;
    public boolean orient = true;
    public boolean orientPitch = true;
    public int maxSteps = 0;
    public boolean cycleMiniStep = true;

    public String yaw = "0";
    public String pitch = "0";
    public String roll = "0";
    public double yOffset = 0;

    public boolean rotateEquation = true;
    public boolean rotateEquation2 = true;
    /**
     * normalize
     * if this is true, the values of t and t2 have an interval of [0,1), regardless of duration, particles and particles2.
     */
    public boolean normalize = true;

    private EquationTransform xTransform, yTransform, zTransform, x2Transform, y2Transform, z2Transform, pitchTransform, yawTransform, rollTransform;

    private double step = 0;
    private double miniStep = 0;

    public EnhancedEquationEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 100;
        constants.put("R", ThreadLocalRandom.current().nextDouble());
        constants.put("t",0D);
    }

    @Override
    public void reset() {
        this.step = 0;
        this.miniStep = 0;
    }

    @Override
    public void onRun() {

        boolean check = (x2Equation != null && y2Equation != null && z2Equation != null && particles2 > 0);
        if (xTransform == null) {
            if (check) constants.put("t2",0D);


            String[] var = constants.keySet().toArray(new String[0]);

            pitchTransform = EquationStore.getInstance().getTransform(pitch,var);
            yawTransform = EquationStore.getInstance().getTransform(yaw,var);
            rollTransform = EquationStore.getInstance().getTransform(roll,var);

            xTransform = EquationStore.getInstance().getTransform(xEquation, var);
            yTransform = EquationStore.getInstance().getTransform(yEquation, var);
            zTransform = EquationStore.getInstance().getTransform(zEquation, var);

            if (check) {
                x2Transform = EquationStore.getInstance().getTransform(x2Equation, var);
                y2Transform = EquationStore.getInstance().getTransform(y2Equation, var);
                z2Transform = EquationStore.getInstance().getTransform(z2Equation, var);
            }
        }
        Location location = getLocation();

        for (int i = 0; i < particles; i++) {
            double[] var = constants.values().stream().mapToDouble(Double::doubleValue).toArray();

            Vector result = new Vector(xTransform.get(var), yTransform.get(var), zTransform.get(var));

            if (rotateEquation) LocationUtil.getInstance().rotate(result,pitchTransform.get(var),yawTransform.get(var),rollTransform.get(var));
            result = rotate(result,location);

            Location targetLocation = location.clone().add(0,yOffset,0);
            targetLocation.add(result);

            if (!check) {
                display(particle, targetLocation);
            } else {
                IntStream.range(0,particles2).forEach(q -> {
                    constants.put("t2",(normalize) ? miniStep/(double)particles2 : miniStep);

                    double[] var2 = constants.values().stream().mapToDouble(Double::doubleValue).toArray();
                    Vector result2 = new Vector(x2Transform.get(var2), y2Transform.get(var2), z2Transform.get(var2));

                    if (rotateEquation2) LocationUtil.getInstance().rotate(result2,pitchTransform.get(var2),yawTransform.get(var2),rollTransform.get(var2));
                    result2 = rotate(result2,location);

                    display(particle, targetLocation.clone().add(result2));

                    miniStep++;
                });

                if (cycleMiniStep) {
                    miniStep = 0;
                }
            }
            if (maxSteps != 0 && step > maxSteps) {
                step = 0;
                break;
            } else {
                step++;
                constants.put("t",(normalize) ? step/(double)particles/(Math.max((double)duration/50,1D)) : step);
            }
        }
    }

    private Vector rotate(Vector v, Location loc) {
        if (orient && orientPitch) {
            return VectorUtils.rotateVector(v, loc);
        } else if (orient) {
            return VectorUtils.rotateVector(v, loc.getYaw(), 0);
        }
        return v;
    }

    public static void addConstant(@Nonnull String name, double constant) {
        c.put(name,constant);
    }
}