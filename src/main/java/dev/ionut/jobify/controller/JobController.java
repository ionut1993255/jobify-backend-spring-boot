package dev.ionut.jobify.controller;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.domain.dto.response.ApiResponse;
import dev.ionut.jobify.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping(path = "/jobs")
    public ResponseEntity<ApiResponse<JobDto>> createJob(@RequestBody JobDto jobDto) {
        JobDto createdJobDto = jobService.createJob(jobDto);

        HttpStatus httpStatusCreated = HttpStatus.CREATED;

        ApiResponse<JobDto> apiResponse = new ApiResponse<>(
                "The job with ID " + createdJobDto.getId() + " was created successfully!",
                httpStatusCreated,
                createdJobDto
        );

        return new ResponseEntity<>(apiResponse, httpStatusCreated);
    }

    @GetMapping(path = "/jobs")
    public ResponseEntity<List<JobDto>> getAllJobs() {
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }
}
