package dev.ionut.jobify.controller;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.domain.dto.response.ApiResponse;
import dev.ionut.jobify.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/jobs/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable int id) {
        return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/jobs/{id}")
    public ResponseEntity<ApiResponse<JobDto>> updateJob(@PathVariable int id, @RequestBody JobDto jobDto) {
        JobDto updatedJobDto = jobService.updateJob(id, jobDto);

        HttpStatus httpStatusOk = HttpStatus.OK;

        ApiResponse<JobDto> apiResponse = new ApiResponse<>(
                "The job with ID " + updatedJobDto.getId() + " was updated successfully!",
                httpStatusOk,
                updatedJobDto
        );

        return new ResponseEntity<>(apiResponse, httpStatusOk);
    }

    @DeleteMapping(path = "/jobs/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);

        HttpStatus httpStatusOk = HttpStatus.OK;

        ApiResponse<Void> apiResponse = new ApiResponse<>(
                "The job with ID " + id + " was deleted successfully!",
                httpStatusOk
        );

        return new ResponseEntity<>(apiResponse, httpStatusOk);
    }
}
