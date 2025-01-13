package com.github.jaeukkang12.job.job.skills;

import com.github.jaeukkang12.job.fish.FishBuilder;
import com.github.jaeukkang12.job.job.Job;
import com.github.jaeukkang12.job.job.JobManager;
import com.github.jaeukkang12.lib.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import static com.github.jaeukkang12.job.Job.getJobManager;

public class Fisher implements Listener {
    @EventHandler
    public void PlayerFishEvent(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();
            event.getCaught().remove();

            ItemStack fish = new FishBuilder(player).build();
            if (!InventoryUtil.hasEnoughSpace(player.getInventory(), fish)) {
                player.sendMessage("인벤토리 공간이 부족해 물고기가 달아났습니다!");
                return;
            }

            String fishName = fish.getItemMeta().getDisplayName();
            if (fishName.contains("S")) {
                Bukkit.broadcastMessage(player.getName() + "님이 " + fishName + "을/를 낚았습니다!");
            }
            player.sendMessage(fishName + "을/를 낚았습니다.");
            player.getInventory().addItem(fish);
        }
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            JobManager jobManager = getJobManager();
            if (jobManager.getJob(player) == Job.FISHER && jobManager.getClass(player) >= 3 && event.getDamageSource().getDamageType().equals(DamageType.DROWN)) {
                event.setCancelled(true);
                player.setRemainingAir(15*20);
            }
        }
    }
}
