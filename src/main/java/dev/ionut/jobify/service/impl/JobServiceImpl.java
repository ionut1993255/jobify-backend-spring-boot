package dev.ionut.jobify.service.impl;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.domain.entity.JobEntity;
import dev.ionut.jobify.exception.ResourceNotFoundException;
import dev.ionut.jobify.mapper.Mapper;
import dev.ionut.jobify.repository.JobRepository;
import dev.ionut.jobify.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final Mapper<JobEntity, JobDto> jobMapper;

    public JobServiceImpl(JobRepository jobRepository, Mapper<JobEntity, JobDto> jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public JobDto createJob(JobDto jobDto) {
        JobEntity jobEntity = jobMapper.mapFrom(jobDto);

        JobEntity createdJobEntity = jobRepository.save(jobEntity);

        return jobMapper.mapTo(createdJobEntity);
    }

    @Override
    public List<JobDto> getAllJobs() {
        return StreamSupport
                .stream(jobRepository.findAll().spliterator(), false)
                .map(jobMapper::mapTo)
                .toList();
    }

    @Override
    public JobDto getJobById(int jobId) {
        return jobRepository
                .findById(jobId)
                .map(jobMapper::mapTo)
                .orElseThrow(() -> new ResourceNotFoundException("The job with ID " + jobId + " does not exist!"));
    }

    @Override
    public JobDto updateJob(int jobId, JobDto jobDto) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("The job with ID " + jobId + " does not exist!");
        }

        JobEntity jobEntity = jobMapper.mapFrom(jobDto);
        jobEntity.setId(jobId);

        JobEntity updatedJobEntity = jobRepository.save(jobEntity);

        return jobMapper.mapTo(updatedJobEntity);
    }

    @Override
    public void deleteJob(int jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("The job with ID " + jobId + " does not exist!");
        }

        jobRepository.deleteById(jobId);
    }
}
