package dev.ionut.jobify.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.service.JobService;
import dev.ionut.jobify.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class JobControllerIntegrationTests {

    private final JobService jobService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public JobControllerIntegrationTests(
            JobService jobService,
            MockMvc mockMvc,
            ObjectMapper objectMapper
    ) {
        this.jobService = jobService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateJobReturnsHttpStatusCreatedSuccessMessageAndSavedJob() throws Exception {
        // Arrange
        JobDto jobDto = TestDataUtil.createTestJobDto();
        String jobJson = objectMapper.writeValueAsString(jobDto);

        // Act
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jobJson)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isCreated()
                )
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        JsonNode dataNode = jsonNode.get("data");
        int jobId = dataNode.get("id").asInt();
        String actualMessage = jsonNode.get("message").asText();
        JobDto savedJob = objectMapper.treeToValue(dataNode, JobDto.class);

        String expectedSuccessMessage = "The job with ID " + jobId + " was created successfully!";

        // Assert
        assertThat(actualMessage).isEqualTo(expectedSuccessMessage);
        assertThat(savedJob.getId()).isEqualTo(jobId);
        assertThat(savedJob)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(jobDto);
    }

    @Test
    public void testThatGetAllJobsReturnsHttpStatusOkAndListOfJobs() throws Exception {
        // Arrange
        JobDto jobDto = TestDataUtil.createTestJobDto();
        JobDto createdJob = jobService.createJob(jobDto);

        // Act
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk()
                )
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        List<JobDto> jobs = objectMapper.readValue(
                responseBody,
                objectMapper.getTypeFactory().constructCollectionType(List.class, JobDto.class)
        );

        // Assert
        assertThat(jobs)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(createdJob);
    }

    @Test
    public void testThatGetJobByIdReturnsHttpStatusOkAndSavedJobWhenJobExists() throws Exception {
        // Arrange
        JobDto jobDto = TestDataUtil.createTestJobDto();
        JobDto createdJob = jobService.createJob(jobDto);
        int jobId = createdJob.getId();

        // Act
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/jobs/" + jobId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk()
                )
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JobDto savedJob = objectMapper.readValue(responseBody, JobDto.class);

        // Assert
        assertThat(savedJob.getId()).isEqualTo(jobId);
        assertThat(savedJob)
                .usingRecursiveComparison()
                .isEqualTo(createdJob);
    }

    @Test
    public void testThatGetJobByIdReturnsHttpStatusNotFoundAndErrorMessageWhenJobDoesNotExist() throws Exception {
        // Arrange
        JobDto jobDto = TestDataUtil.createTestJobDto();
        JobDto createdJob = jobService.createJob(jobDto);
        int jobId = createdJob.getId();

        jobService.deleteJob(jobId);

        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";

        mockMvc
                // Act
                .perform(
                        MockMvcRequestBuilders
                                .get("/jobs/" + jobId)
                                .contentType(MediaType.APPLICATION_JSON)
                )

                // Assert
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isNotFound()
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.message")
                                .value(expectedErrorMessage)
                );
    }

    @Test
    public void testThatUpdateJobReturnsHttpStatusOkSuccessMessageAndUpdatedJobWhenJobExists() throws Exception {
        // Arrange
        JobDto originalJob = TestDataUtil.createTestJobDto();
        JobDto createdJob = jobService.createJob(originalJob);
        int jobId = createdJob.getId();

        JobDto updatedJobDto = TestDataUtil.createTestJobDto();
        updatedJobDto.setTitle("Updated Title");
        String updatedJobJson = objectMapper.writeValueAsString(updatedJobDto);

        // Act
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/jobs/" + jobId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedJobJson)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk()
                )
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        JsonNode dataNode = jsonNode.get("data");
        String actualMessage = jsonNode.get("message").asText();
        JobDto updatedJob = objectMapper.treeToValue(dataNode, JobDto.class);

        String expectedSuccessMessage = "The job with ID " + jobId + " was updated successfully!";

        // Assert
        assertThat(actualMessage).isEqualTo(expectedSuccessMessage);
        assertThat(updatedJob.getId()).isEqualTo(jobId);
        assertThat(updatedJob)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(updatedJobDto);
    }

    @Test
    public void testThatUpdateJobReturnsHttpStatusNotFoundAndErrorMessageWhenJobDoesNotExist() throws Exception {
        // Arrange
        JobDto originalJob = TestDataUtil.createTestJobDto();
        JobDto createdJob = jobService.createJob(originalJob);
        int jobId = createdJob.getId();

        jobService.deleteJob(jobId);

        JobDto updatedJob = TestDataUtil.createTestJobDto();
        updatedJob.setTitle("Updated Title");
        String jobJson = objectMapper.writeValueAsString(updatedJob);

        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";

        mockMvc
                // Act
                .perform(
                        MockMvcRequestBuilders
                                .put("/jobs/" + jobId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jobJson)

                )

                // Assert
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isNotFound()
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.message")
                                .value(expectedErrorMessage)
                );
    }

    @Test
    public void testThatDeleteJobReturnsHttpStatusOkAndSuccessMessageWhenJobExists() throws Exception {
        // Arrange
        JobDto jobDto = TestDataUtil.createTestJobDto();
        JobDto createdJob = jobService.createJob(jobDto);
        int jobId = createdJob.getId();

        String expectedSuccessMessage = "The job with ID " + jobId + " was deleted successfully!";

        mockMvc
                // Act
                .perform(
                        MockMvcRequestBuilders
                                .delete("/jobs/" + jobId)
                                .contentType(MediaType.APPLICATION_JSON)
                )

                // Assert
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk()
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.message")
                                .value(expectedSuccessMessage)
                );
    }

    @Test
    public void testThatDeleteJobReturnsHttpStatusNotFoundAndErrorMessageWhenJobDoesNotExist() throws Exception {
        // Arrange
        JobDto jobDto = TestDataUtil.createTestJobDto();
        JobDto createdJob = jobService.createJob(jobDto);
        int jobId = createdJob.getId();

        jobService.deleteJob(jobId);

        String expectedErrorMessage = "The job with ID " + jobId + " does not exist!";

        mockMvc
                // Act
                .perform(
                        MockMvcRequestBuilders
                                .delete("/jobs/" + jobId)
                                .contentType(MediaType.APPLICATION_JSON)
                )

                // Assert
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isNotFound()
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.message")
                                .value(expectedErrorMessage)
                );
    }
}
