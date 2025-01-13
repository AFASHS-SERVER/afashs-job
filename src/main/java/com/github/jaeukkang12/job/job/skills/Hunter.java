package com.github.jaeukkang12.job.job.skills;

import org.bukkit.entity.Player;

public class Hunter {
    public static void clazz(Player player, int clazz) {
        if (clazz == 1) {
            player.setHealthScale(10);
        } else if (clazz == 2) {
            player.setHealthScale(12);
        } else if (clazz <= 4) {
            player.setHealthScale(16);
        }
        player.setHealth(player.getHealthScale());
    }

    public static void reset(Player player) {
        player.setHealthScale(20);
        player.setHealth(20);
    }
}
