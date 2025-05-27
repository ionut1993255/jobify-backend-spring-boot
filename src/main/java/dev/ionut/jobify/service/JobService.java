package dev.ionut.jobify.service;

import dev.ionut.jobify.domain.dto.JobDto;

import java.util.List;

public interface JobService {
    JobDto createJob(JobDto jobDto);

    List<JobDto> getAllJobs();

    JobDto getJobById(int jobId);

    JobDto updateJob(int jobId, JobDto jobDto);

    void deleteJob(int jobId);
}
