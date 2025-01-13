package com.github.jaeukkang12.job.job;

import com.github.jaeukkang12.lib.job.JobManagerImpl;
import org.bukkit.entity.Player;

import static com.github.jaeukkang12.job.Job.jobData;

public class JobManager implements JobManagerImpl {
    public JobManager() {
    }

    public void init(Player target) {
        setJob(target, Job.NONE);
        setClass(target, 0);
    }

    @Override
    public Enum getJob(Player target) {
        if (!jobData.isExist(target.getUniqueId() + "")) {
            init(target);
        }
        return Job.valueOf(jobData.getString(target.getUniqueId() + ".job"));
    }

    @Override
    public int getClass(Player target) {
        if (!jobData.isExist(target.getUniqueId() + "")) {
            init(target);
        }
        return jobData.getInt(target.getUniqueId() + ".class");
    }

    @Override
    public void setJob(Player target, Enum job) {
        jobData.setString(target.getUniqueId() + ".job", job.toString());
    }

    @Override
    public void setClass(Player target, int i) {
        jobData.setInt(target.getUniqueId() + ".class", i);
    }
}
