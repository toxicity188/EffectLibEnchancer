package kor.toxicity.effectlibenhancer;

import de.slikey.effectlib.EffectManager;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class EffectLibEnhancer extends JavaPlugin {

    private static EffectLibEnhancer me;
    private EffectManager manager;

    @Override
    public void onEnable() {
        me = this;
        manager = new EffectManager(this);

        Bukkit.getWorlds().forEach(w ->
             w.getEntities().forEach(e -> {
                if (e instanceof ArmorStand && e.getCustomName() != null && e.getCustomName().equals("EffectLibEnhancer")) e.remove();
            })
        );
        Bukkit.getConsoleSender().sendMessage("[EffectLibEnhancer] Plugin successfully enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[EffectLibEnhancer] Plugin successfully disabled.");
    }

    public static BukkitTask runTaskTimer(Runnable task, long delay, long time) {
        return Bukkit.getScheduler().runTaskTimer(me,task,delay,time);
    }
    public static void display(Particle particle, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, float size, Color color, Material material, byte materialData, double range) {
        me.manager.display(particle,center,offsetX,offsetY,offsetZ,speed,amount,size,color,material,materialData,range,null);
    }
}
