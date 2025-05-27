package dev.ionut.jobify.util;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.domain.entity.JobEntity;

import java.time.LocalDate;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static JobEntity createTestJobEntity() {
        return JobEntity.builder()
                .title("Software Developer")
                .type("Full-Time")
                .description("Build and maintain applications.")
                .salary("$90K - $110K")
                .location("Remote")
                .postedDate(LocalDate.now())
                .active(true)
                .experienceLevel("Mid")
                .companyName("TechCorp Inc.")
                .companyDescription("A fast-growing tech company.")
                .companyContactEmail("hr@techcorp.com")
                .companyContactPhone("123-456-7890")
                .build();
    }

    public static JobDto createTestJobDto() {
        return JobDto.builder()
                .title("Software Developer")
                .type("Full-Time")
                .description("Build and maintain applications.")
                .salary("$90K - $110K")
                .location("Remote")
                .postedDate(LocalDate.now())
                .active(true)
                .experienceLevel("Mid")
                .companyName("TechCorp Inc.")
                .companyDescription("A fast-growing tech company.")
                .companyContactEmail("hr@techcorp.com")
                .companyContactPhone("123-456-7890")
                .build();
    }
}
