package com.github.jaeukkang12.job;

import com.github.jaeukkang12.job.command.JobCommand;
import com.github.jaeukkang12.job.job.JobManager;
import com.github.jaeukkang12.job.job.skills.Farmer;
import com.github.jaeukkang12.job.job.skills.Fisher;
import com.github.jaeukkang12.job.job.skills.Miner;
import com.github.jaeukkang12.lib.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Job extends JavaPlugin {

    private static JavaPlugin plugin;

    // JOB DATA
    public static Config jobData;
    // CONFIG
    public static Config config;

    // JOB MANAGER
    private static JobManager jobManager;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("afashs-lib") == null) {
            Bukkit.getLogger().warning("[" + this.getName() + "]" + "afashs-lib 플러그인이 감지되지 않았습니다! 플러그인을 종료합니다.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // INSTANCE
        plugin = this;

        // CONFIG
        config = new Config("config", plugin);
        config.setPrefix("prefix");
        config.loadDefaultConfig();

        // JOB DATA
        jobData = new Config("jobData", plugin);
        jobData.loadDefaultConfig();

        // JOB MANAGER
        jobManager = new JobManager();

        // EVENT
        Bukkit.getPluginManager().registerEvents(new Miner(), plugin);
        Bukkit.getPluginManager().registerEvents(new Farmer(), plugin);
        Bukkit.getPluginManager().registerEvents(new Fisher(), plugin);

        // COMMAND
        Bukkit.getPluginCommand("jobadmin").setExecutor(new JobCommand());
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static JobManager getJobManager() {
        return jobManager;
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(plugin);
    }
}
