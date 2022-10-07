package de.slikey.effectlib.effect;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import kor.toxicity.effectlibenhancer.EffectLibEnhancer;
import kor.toxicity.effectlibenhancer.physics.PhysicsEffect;
import kor.toxicity.effectlibenhancer.physics.PhysicsObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

public final class PhysicsArmorStandEffect extends PhysicsEffect {

    public String item = "STONE";
    public int durability = 0;
    public boolean pitch = true;
    public boolean small = false;

    private ArmorStand stand;


    public PhysicsArmorStandEffect(EffectManager effectManager) {
        super(effectManager);
        asynchronous = false;
        type = EffectType.REPEATING;
        duration = 0;
        period = 1;
        iterations = 100;
    }

    @Override
    public void access(Location loc) {
        new ArmorStandRun(loc,rand(v),this);
    }

    @Override
    public void show(Location location) {
    }

    private static class ArmorStandRun implements Runnable {

        private final PhysicsArmorStandEffect e;
        private final ArmorStand stand;

        private int loop = 0;
        private final PhysicsObject physics;
        private final BukkitTask task;

        private ArmorStandRun(Location location, double v, PhysicsArmorStandEffect e) {
            this.e = e;
            physics = new PhysicsObject(location, v, e.interval, e.gravity, e.bounceAmplifier, e.bounceAmount);
            task = EffectLibEnhancer.runTaskTimer(this, 0, e.interval);

            stand = (ArmorStand) e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.ARMOR_STAND);
            stand.setCustomName("EffectLibEnhancer");
            stand.setInvulnerable(true);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setCollidable(false);
            stand.setSilent(true);
            stand.setCustomNameVisible(false);
            stand.setSmall(e.small);

            try {
                ItemStack item = new ItemStack(Material.valueOf(e.item));
                item.setDurability((short) e.durability);
                stand.setHelmet(item);
            } catch (Exception t) {
                stand.setHelmet(new ItemStack(Material.STONE));
            }
        }


        @Override
        public void run() {
            loop++;
            physics.update();
            stand.teleport(physics.getLocation());
            if (e.pitch) {
                stand.setHeadPose(new EulerAngle(physics.getDegree(), 0,0));
            }
            if (loop >= e.objectDuration) {
                stand.remove();
                task.cancel();
            }
        }
    }
}
