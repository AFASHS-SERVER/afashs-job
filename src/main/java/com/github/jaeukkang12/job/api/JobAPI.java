package com.github.jaeukkang12.job.api;

import com.github.jaeukkang12.job.Job;
import com.github.jaeukkang12.job.job.JobManager;

public class JobAPI {
    public JobAPI() {
    }

    public JobManager getJobManager() {
        return Job.getJobManager();
    }
}
