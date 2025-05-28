package dev.ionut.jobify.service;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.domain.entity.JobEntity;
import dev.ionut.jobify.exception.ResourceNotFoundException;
import dev.ionut.jobify.mapper.Mapper;
import dev.ionut.jobify.repository.JobRepository;
import dev.ionut.jobify.service.impl.JobServiceImpl;
import dev.ionut.jobify.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceImplTests {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private Mapper<JobEntity, JobDto> jobMapper;

    @InjectMocks
    private JobServiceImpl underTest;

    @Test
    public void testThatCreateJobReturnsJobDto() {
        // Arrange
        JobDto inputJobDto = TestDataUtil.createTestJobDto();
        JobEntity mappedJobEntity = TestDataUtil.createTestJobEntity();
        JobEntity savedJobEntity = TestDataUtil.createTestJobEntity();
        JobDto returnedJobDto = TestDataUtil.createTestJobDto();

        when(jobMapper.mapFrom(inputJobDto)).thenReturn(mappedJobEntity);
        when(jobRepository.save(mappedJobEntity)).thenReturn(savedJobEntity);
        when(jobMapper.mapTo(savedJobEntity)).thenReturn(returnedJobDto);

        // Act
        JobDto createdJobDto = underTest.createJob(inputJobDto);

        // Assert
        assertThat(createdJobDto).isEqualTo(returnedJobDto);

        verify(jobMapper).mapFrom(inputJobDto);
        verify(jobRepository).save(mappedJobEntity);
        verify(jobMapper).mapTo(savedJobEntity);
    }

    @Test
    public void testThatGetAllJobsReturnsListOfJobDtos() {
        // Arrange
        JobEntity firstJobEntity = TestDataUtil.createTestJobEntity();
        JobEntity secondJobEntity = TestDataUtil.createAnotherTestJobEntity();

        JobDto firstJobDto = TestDataUtil.createTestJobDto();
        JobDto secondJobDto = TestDataUtil.createAnotherTestJobDto();

        when(jobRepository.findAll()).thenReturn(List.of(firstJobEntity, secondJobEntity));
        when(jobMapper.mapTo(firstJobEntity)).thenReturn(firstJobDto);
        when(jobMapper.mapTo(secondJobEntity)).thenReturn(secondJobDto);

        // Act
        List<JobDto> retrievedJobDtos = underTest.getAllJobs();

        // Assert
        assertThat(retrievedJobDtos).containsExactly(firstJobDto, secondJobDto);

        verify(jobRepository).findAll();
        verify(jobMapper, times((2))).mapTo(any());
    }

    @Test
    public void testThatGetJobByIdReturnsJobDtoWhenExists() {
        // Arrange
        int jobId = 1;
        JobEntity jobEntity = TestDataUtil.createTestJobEntity();
        jobEntity.setId(jobId);

        JobDto expectedJobDto = TestDataUtil.createTestJobDto();

        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobEntity));
        when(jobMapper.mapTo(jobEntity)).thenReturn(expectedJobDto);

        // Act
        JobDto actualJobDto = underTest.getJobById(jobId);

        // Assert
        assertThat(actualJobDto).isEqualTo(expectedJobDto);

        verify(jobRepository).findById(jobId);
        verify(jobMapper).mapTo(jobEntity);
    }

    @Test
    public void testThatGetJobByIdThrowsAnExceptionWhenJobDoesNotExist() {
        // Arrange
        int jobId = 1;
        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> underTest.getJobById(jobId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(expectedErrorMessage);

        verify(jobRepository).findById(jobId);
        verifyNoInteractions(jobMapper);
    }

    @Test
    public void testThatUpdateJobReturnsUpdatedJobDtoWhenExists() {
        // Arrange
        int jobId = 1;

        JobDto originalJobDto = TestDataUtil.createTestJobDto();
        JobDto expectedUpdatedJobDto = TestDataUtil.createTestJobDto();
        expectedUpdatedJobDto.setTitle("Updated Title");

        JobEntity originalJobEntity = TestDataUtil.createTestJobEntity();
        originalJobEntity.setId(jobId);
        JobEntity updatedJobEntity = TestDataUtil.createTestJobEntity();
        updatedJobEntity.setId(jobId);
        updatedJobEntity.setTitle("Updated Title");

        when(jobRepository.existsById(jobId)).thenReturn(true);
        when(jobMapper.mapFrom(originalJobDto)).thenReturn(updatedJobEntity);
        when(jobRepository.save(updatedJobEntity)).thenReturn(updatedJobEntity);
        when(jobMapper.mapTo(updatedJobEntity)).thenReturn(expectedUpdatedJobDto);

        // Act
        JobDto actualUpdatedJobDto = underTest.updateJob(jobId, originalJobDto);

        // Assert
        assertThat(actualUpdatedJobDto).isEqualTo(expectedUpdatedJobDto);

        verify(jobRepository).existsById(jobId);
        verify(jobMapper).mapFrom(originalJobDto);
        verify(jobRepository).save(updatedJobEntity);
        verify(jobMapper).mapTo(updatedJobEntity);
    }

    @Test
    public void testThatUpdateJobThrowsAnExceptionWhenJobDoesNotExist() {
        // Arrange
        int jobId = 1;
        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";
        JobDto jobDto = TestDataUtil.createTestJobDto();

        when(jobRepository.existsById(jobId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> underTest.updateJob(jobId, jobDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(expectedErrorMessage);

        verify(jobRepository).existsById(jobId);
        verifyNoMoreInteractions(jobRepository);
        verifyNoInteractions(jobMapper);
    }

    @Test
    public void testThatDeleteJobRemovesJobWhenExists() {
        // Arrange
        int jobId = 1;
        when(jobRepository.existsById(jobId)).thenReturn(true);

        // Act
        underTest.deleteJob(jobId);

        // Assert
        verify(jobRepository).existsById(jobId);
        verify(jobRepository).deleteById(jobId);
    }

    @Test
    public void testThatDeleteJobThrowsAnExceptionWhenJobDoesNotExist() {
        // Arrange
        int jobId = 1;
        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";
        when(jobRepository.existsById(jobId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> underTest.deleteJob(jobId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(expectedErrorMessage);

        verify(jobRepository).existsById(jobId);
        verify(jobRepository, never()).deleteById(anyInt());
    }
}
