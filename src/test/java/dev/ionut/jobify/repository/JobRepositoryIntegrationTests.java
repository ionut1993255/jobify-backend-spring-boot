package dev.ionut.jobify.repository;

import dev.ionut.jobify.domain.entity.JobEntity;
import dev.ionut.jobify.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JobRepositoryIntegrationTests {

    private final JobRepository underTest;

    @Autowired
    public JobRepositoryIntegrationTests(JobRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatJobCanBeCreatedAndRetrieved() {
        // Arrange
        JobEntity jobToSave = TestDataUtil.createTestJobEntity();

        // Act
        underTest.save(jobToSave);
        Optional<JobEntity> retrievedCreatedJob = underTest.findById(jobToSave.getId());

        // Assert
        assertThat(retrievedCreatedJob).isPresent();
        assertThat(retrievedCreatedJob.get())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(jobToSave);
    }

    @Test
    public void testThatAllJobsCanBeRetrieved() {
        // Arrange
        JobEntity firstJobToSave = TestDataUtil.createTestJobEntity();
        JobEntity secondJobToSave = TestDataUtil.createAnotherTestJobEntity();

        // Act
        underTest.save(firstJobToSave);
        underTest.save(secondJobToSave);
        List<JobEntity> retrievedJobs = StreamSupport
                .stream(underTest.findAll().spliterator(), false)
                .toList();

        // Assert
        assertThat(retrievedJobs).hasSize(2);
        assertThat(retrievedJobs)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(firstJobToSave, secondJobToSave));
    }

    @Test
    public void testThatJobCanBeUpdatedAndRetrieved() {
        // Arrange
        JobEntity originalJob = TestDataUtil.createTestJobEntity();
        underTest.save(originalJob);
        originalJob.setTitle("Title Updated");

        // Act
        underTest.save(originalJob);
        Optional<JobEntity> retrievedUpdatedJob = underTest.findById(originalJob.getId());

        // Assert
        assertThat(retrievedUpdatedJob).isPresent();
        assertThat(retrievedUpdatedJob.get().getTitle()).isEqualTo(originalJob.getTitle());
    }

    @Test
    public void testThatJobCanBeDeleted() {
        // Arrange
        JobEntity jobToSave = TestDataUtil.createTestJobEntity();

        // Act
        JobEntity savedJob = underTest.save(jobToSave);
        int savedJobId = savedJob.getId();
        underTest.deleteById(savedJobId);
        Optional<JobEntity> retrievedJobAfterDeletion = underTest.findById(savedJobId);

        // Assert
        assertThat(retrievedJobAfterDeletion).isEmpty();
    }
}
