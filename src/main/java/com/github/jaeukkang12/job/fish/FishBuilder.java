package com.github.jaeukkang12.job.fish;

import com.github.jaeukkang12.job.job.JobManager;
import com.github.jaeukkang12.lib.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.github.jaeukkang12.job.Job.getJobManager;

public class FishBuilder {

    private final Player player;
    private ItemBuilder itemBuilder;
    private final int clazz;
    private final JobManager jobManager;
    private final Enum job;

    public FishBuilder(Player player) {
        this.player = player;
        jobManager = getJobManager();
        job = jobManager.getJob(player);
        clazz = jobManager.getClass(player);
    }

    private void selectItem(double p, List<FishEntry> entries) {
        double minP = 0;
        for (FishEntry entry : entries) {
            if (p < entry.probability && p >= minP) {
                itemBuilder = new ItemBuilder(entry.material).setDisplayName(entry.name);
                if (entry.customModelData != null) {
                    itemBuilder.setCustomModelData(entry.customModelData);
                }
                break;
            }
            minP = entry.probability;
        }
    }

    private void setMaterial() {
        double p = Math.random();
        if (job != com.github.jaeukkang12.job.job.Job.FISHER) {
            selectItem(p, List.of(
                    new FishEntry(0.6, Material.COD, "&f대구"),
                    new FishEntry(0.85, Material.SALMON, "&f연어"),
                    new FishEntry(0.87, Material.TROPICAL_FISH, "&f열대어"),
                    new FishEntry(1.0, Material.PUFFERFISH, "&f복어")
            ));
            return;
        }

        if (clazz >= 2) {
            selectItem(p, List.of(
                    new FishEntry(0.2, Material.COD, "&f대구"),
                    new FishEntry(0.35, Material.SALMON, "&f연어"),
                    new FishEntry(0.37, Material.TROPICAL_FISH, "&f열대어"),
                    new FishEntry(0.5, Material.PUFFERFISH, "&f복어"),
                    new FishEntry(0.6, Material.COD, "&f송어", 10000),
                    new FishEntry(0.7, Material.COD, "&f명태", 10001),
                    new FishEntry(0.8, Material.COD, "&f우럭", 10002),
                    new FishEntry(0.9, Material.COD, "&f장어", 10003),
                    new FishEntry(1.0, Material.COD, "&f잉어", 10004)
            ));
        } else if (clazz >= 1) {
            selectItem(p, List.of(
                    new FishEntry(0.15, Material.COD, "&f대구"),
                    new FishEntry(0.3, Material.SALMON, "&f연어"),
                    new FishEntry(0.32, Material.TROPICAL_FISH, "&f열대어"),
                    new FishEntry(0.4, Material.PUFFERFISH, "&f복어"),
                    new FishEntry(0.45, Material.COD, "&f송어", 10000),
                    new FishEntry(0.55, Material.COD, "&f명태", 10001),
                    new FishEntry(0.6, Material.COD, "&f우럭", 10002),
                    new FishEntry(0.65, Material.COD, "&f장어", 10003),
                    new FishEntry(0.7, Material.COD, "&f잉어", 10004),
                    new FishEntry(0.72, Material.COD, "&f참치", 10005),
                    new FishEntry(0.74, Material.COD, "&f청새치", 10006),
                    new FishEntry(0.84, Material.COD, "&f가자미", 10007),
                    new FishEntry(0.94, Material.COD, "&f고등어", 10008),
                    new FishEntry(1.0, Material.COD, "&f청어", 10009)
            ));
        }
    }

    private void setRank() {
       List<String> ranks = List.of("&eS", "&aA", "&9B", "&dC", "&7D", "&8E", "&0F");
       int index;
       double random = Math.random();
       if (random < 0.03) {
           index = 0;
       } else if (random < 0.08) {
           index = 1;
       } else if (random < 0.18) {
           index = 2;
       } else if (random < 0.4) {
           index = 3;
       } else if (random < 0.6) {
           index = 4;
       } else if (random < 0.8) {
           index = 5;
       } else {
           index = 6;
       }

       if (clazz >= 3 && Math.random() < 0.1 && index != 0) {
           player.sendMessage("낚시꾼의 손재주로 물고기 등급이 1 올랐습니다!");
           index -= 1;
       }

       itemBuilder.setDisplayName("&f&l[" + ranks.get(index) + "&f&l] " + itemBuilder.build().getItemMeta().getDisplayName());

    }

    public ItemStack build() {
        setMaterial();
        setRank();
        return itemBuilder.build();
    }
}
