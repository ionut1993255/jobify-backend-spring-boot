package dev.ionut.jobify.service;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.exception.ResourceNotFoundException;
import dev.ionut.jobify.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JobServiceIntegrationTests {

    private final JobService underTest;

    @Autowired
    public JobServiceIntegrationTests(JobService underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatCreatedJobAppearsInGetAllJobs() {
        // Arrange
        JobDto newJob = TestDataUtil.createTestJobDto();

        // Act
        JobDto createdJob = underTest.createJob(newJob);
        List<JobDto> allJobs = underTest.getAllJobs();

        // Assert
        assertThat(createdJob.getId()).isNotNull();
        assertThat(createdJob)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newJob);
        assertThat(allJobs)
                .anySatisfy(job -> {
                    assertThat(job.getId()).isNotNull();
                    assertThat(job)
                            .usingRecursiveComparison()
                            .ignoringFields("id")
                            .isEqualTo(newJob);
                });
    }

    @Test
    public void testThatGetJobByIdRetrievesExistingJob() {
        // Arrange
        JobDto newJob = TestDataUtil.createTestJobDto();

        // Act
        JobDto createdJob = underTest.createJob(newJob);
        int jobId = createdJob.getId();
        JobDto retrievedJob = underTest.getJobById(jobId);

        // Assert
        assertThat(retrievedJob.getId()).isEqualTo(jobId);
        assertThat(retrievedJob)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newJob);
    }

    @Test
    public void testThatGetJobByIdThrowsAnExceptionWhenJobWasNotFound() {
        // Arrange
        JobDto newJob = TestDataUtil.createTestJobDto();

        // Act
        JobDto createdJob = underTest.createJob(newJob);
        int jobId = createdJob.getId();
        underTest.deleteJob(jobId);

        // Assert
        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";
        assertThatThrownBy(() -> underTest.getJobById(jobId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(expectedErrorMessage);
    }

    @Test
    public void testThatUpdateJobRetrievesModifiedJobFromList() {
        // Arrange
        JobDto newJob = TestDataUtil.createTestJobDto();

        // Act
        JobDto createdJob = underTest.createJob(newJob);
        createdJob.setTitle("Updated Title");
        int jobId = createdJob.getId();
        String jobTitle = createdJob.getTitle();

        JobDto updatedJob = underTest.updateJob(jobId, createdJob);
        List<JobDto> allJobs = underTest.getAllJobs();

        // Assert
        assertThat(updatedJob.getId()).isEqualTo(jobId);
        assertThat(updatedJob.getTitle()).isEqualTo(jobTitle);
        assertThat(allJobs)
                .anySatisfy(job -> {
                    assertThat(job.getId()).isEqualTo(updatedJob.getId());
                    assertThat(job.getTitle()).isEqualTo(updatedJob.getTitle());
                });
    }

    @Test
    public void testThatUpdateJobThrowsAnExceptionWhenJobWasNotFound() {
        // Arrange
        JobDto newJob = TestDataUtil.createTestJobDto();

        // Act
        JobDto createdJob = underTest.createJob(newJob);
        int jobId = createdJob.getId();
        underTest.deleteJob(jobId);

        // Assert
        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";
        assertThatThrownBy(() -> underTest.updateJob(jobId, createdJob))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(expectedErrorMessage);
    }

    @Test
    public void testThatDeleteJobRemovesCreatedJobFromList() {
        // Arrange
        JobDto newJob = TestDataUtil.createTestJobDto();

        // Act
        JobDto createdJob = underTest.createJob(newJob);
        int jobId = createdJob.getId();
        underTest.deleteJob(jobId);
        List<JobDto> allJobs = underTest.getAllJobs();

        // Assert
        assertThat(allJobs).noneMatch(job -> Objects.equals(job.getId(), jobId));
    }

    @Test
    public void testThatDeleteNonExistingJobThrowsAnException() {
        // Arrange
        JobDto newJob = TestDataUtil.createTestJobDto();

        // Act
        JobDto createdJob = underTest.createJob(newJob);
        int jobId = createdJob.getId();
        underTest.deleteJob(jobId);

        // Assert
        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";
        assertThatThrownBy(() -> underTest.deleteJob(jobId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(expectedErrorMessage);
    }
}
