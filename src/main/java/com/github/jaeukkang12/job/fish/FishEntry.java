package com.github.jaeukkang12.job.fish;

import org.bukkit.Material;

public class FishEntry {
    double probability;
    Material material;
    String name;
    Integer customModelData;

    public FishEntry(double probability, Material material, String name) {
        this(probability, material, name, null);
    }

    public FishEntry(double probability, Material material, String name, Integer customModelData) {
        this.probability = probability;
        this.material = material;
        this.name = name;
        this.customModelData = customModelData;
    }
}
