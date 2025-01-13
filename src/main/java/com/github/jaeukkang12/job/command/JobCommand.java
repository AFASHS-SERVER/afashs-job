package com.github.jaeukkang12.job.command;

import com.github.jaeukkang12.job.job.Job;
import com.github.jaeukkang12.job.job.JobManager;
import com.github.jaeukkang12.job.job.skills.Hunter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.jaeukkang12.job.Job.*;

public class JobCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command label, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        // HELP
        if (args.length == 0 || args[0].equals("help")) {
            if (!player.hasPermission("afashs.job.help")) {
                player.sendMessage(config.getMessage("errorMessages.noPermission"));
                return true;
            }

            config.getMessages("messages.job.help").forEach(player::sendMessage);
            return true;
        }

        // SET
        if (args[0].equals("set")) {
            if (!player.hasPermission("afashs.job.set")) {
                player.sendMessage(config.getMessage("errorMessages.noPermission"));
                return true;
            }

            if (args.length == 1) {
                player.sendMessage(config.getMessage("errorMessages.nonJobOrClass"));
                return true;
            }

            if (args.length == 2) {
                player.sendMessage(config.getMessage("errorMessages.nonPlayer"));
                return true;
            }

            if (args.length == 3) {
                player.sendMessage(config.getMessage("errorMessages.nonValue"));
                return true;
            }

            String jobOrClass = args[1].toLowerCase();
            if (!(jobOrClass.equals("job") || jobOrClass.equals("class"))) {
                player.sendMessage(config.getMessage("errorMessages.invalidJobOrClass"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                player.sendMessage(config.getMessage("errorMessages.invalidPlayer"));
                return true;
            }

            JobManager jobManager = getJobManager();

            if (jobOrClass.equals("job")) {
                String jobName = args[3];
                try {
                    Job job = Job.valueOf(jobName);
                    jobManager.setJob(target, job);
                    player.sendMessage(config.getMessage("messages.job.set.job")
                            .replace("{target}", target.getName())
                            .replace("{job}", jobName));
                    Hunter.reset(target);
                    if (job == Job.HUNTER) {
                        Hunter.clazz(target, jobManager.getClass(target));
                    }
                    return true;
                } catch (IllegalArgumentException e) {
                    player.sendMessage(config.getMessage("errorMessages.invalidJobName"));
                    return true;
                }
            } else {
                try {
                    int clazz = Integer.parseInt(args[3]);
                    jobManager.setClass(target, clazz);
                    player.sendMessage(config.getMessage("messages.job.set.class")
                            .replace("{target}", target.getName())
                            .replace("{class}", String.valueOf(clazz)));
                    Hunter.reset(target);
                    if (jobManager.getJob(target) == Job.HUNTER) {
                        Hunter.clazz(target, clazz);
                    }
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage(config.getMessage("errorMessages.invalidClass"));
                    return true;
                }
            }
        }

        // RELOAD
        if (args[0].equals("reload")) {
            if (!player.hasPermission("afashs.job.reload")) {
                player.sendMessage(config.getMessage("errorMessages.noPermission"));
                return true;
            }
            jobData.reloadConfig();
            player.sendMessage(config.getMessage("messages.job.reload"));
            return true;
        }

        return false;
    }
}
