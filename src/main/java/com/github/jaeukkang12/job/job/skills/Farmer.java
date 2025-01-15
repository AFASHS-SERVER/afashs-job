package com.github.jaeukkang12.job.job.skills;

import com.github.jaeukkang12.job.job.Job;
import com.github.jaeukkang12.job.job.JobManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.jaeukkang12.job.Job.getJobManager;

public class Farmer implements Listener {

    private final List<Material> plants = List.of(Material.WHEAT, Material.BEETROOTS, Material.PUMPKIN, Material.MELON, Material.COCOA, Material.CACTUS,
                                                    Material.SUGAR_CANE, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.CHORUS_FLOWER, Material.CHORUS_PLANT,
                                                    Material.BAMBOO, Material.KELP_PLANT, Material.KELP, Material.SEAGRASS, Material.TALL_SEAGRASS,
                                                    Material.POTATOES, Material.CARROTS, Material.NETHER_WART, Material.TORCHFLOWER);

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        JobManager jobManager = getJobManager();

        if (jobManager.getJob(player) != Job.FARMER) {
            return;
        }

        int clazz = jobManager.getClass(player);
        Block block = event.getBlock();
        if (plants.contains(block.getType())) {
            if (clazz >= 1) {
                if (Math.random() < 0.1) {
                    block.getDrops().forEach(item -> {
                        item.setAmount((clazz >= 2) ? 2 : 1);
                        block.getWorld().dropItemNaturally(block.getLocation(), item);
                    });
                }
            }
        }
    }

    private void luckSkill(Block block, int amount) {
        Collection<ItemStack> drops = block.getDrops();
        if (Math.random() < 0.1) {
            drops = drops.stream().map(itemStack -> itemStack.add(amount)).collect(Collectors.toList());
        }
        drops.forEach(itemStack -> block.getWorld().dropItemNaturally(block.getLocation(), itemStack));
    }
}
