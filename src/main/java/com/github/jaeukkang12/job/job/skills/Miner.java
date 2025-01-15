package com.github.jaeukkang12.job.job.skills;

import com.github.jaeukkang12.job.job.Job;
import com.github.jaeukkang12.job.job.JobManager;
import com.github.jaeukkang12.lib.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.github.jaeukkang12.job.Job.getJobManager;
import static com.github.jaeukkang12.job.Job.getPlugin;

public class Miner implements Listener {


    final List<Material> ores = List.of(
            Material.STONE, Material.DEEPSLATE, Material.GRANITE, Material.ANDESITE, Material.DIORITE, Material.CALCITE,
            Material.TUFF, Material.DRIPSTONE_BLOCK, Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.NETHERRACK,
            Material.BLACKSTONE, Material.BASALT, Material.SMOOTH_BASALT, Material.END_STONE, Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COAL_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.NETHER_GOLD_ORE, Material.NETHER_QUARTZ_ORE, Material.ANCIENT_DEBRIS);

    private HashMap<Player, Integer> skill1Count = new HashMap<>();
    private HashMap<Player, Boolean> skill1Cooldown = new HashMap<>();

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        JobManager jobManager = getJobManager();

        if (jobManager.getJob(player) != Job.MINER) {
            return;
        }

        int clazz = jobManager.getClass(player);
        Block block = event.getBlock();
        if (ores.contains(block.getType())) {
            if (clazz >= 1) {
                if (skill1Cooldown.get(player) == null) {
                    skill1Count.put(player, (skill1Count.containsKey(player) ? skill1Count.get(player) : 0) + 1);
                    if (skill1Count.get(player) == 50) {
                        hasteSkill(player, (clazz >= 3) ? 2 : 1);
                    }
                }
            }
            if (clazz >= 3) {
                if (Math.random() < 0.1) {
                    block.getDrops().forEach(item -> {
                        item.setAmount(2);
                        block.getWorld().dropItemNaturally(block.getLocation(), item);
                    });
                }
            }
        }
    }

    @EventHandler
    public void playerDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            JobManager jobManager = getJobManager();
            if (jobManager.getJob(player) == Job.MINER) {
                if (jobManager.getClass(player) >= 4) {
                    DamageType damageType = event.getDamageSource().getDamageType();
                    if (damageType.equals(DamageType.LAVA) || damageType.equals(DamageType.ON_FIRE)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private void hasteSkill(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 200, level-1, true, false));
        skill1Cooldown.put(player, true);
        new BukkitRunnable() {
            @Override
            public void run() {
                skill1Count.remove(player);
                skill1Cooldown.remove(player);
            }
        }.runTaskLaterAsynchronously(getPlugin(), 10 * 20L);
    }
}
