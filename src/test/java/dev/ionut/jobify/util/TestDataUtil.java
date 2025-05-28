package dev.ionut.jobify.util;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.domain.entity.JobEntity;

import java.time.LocalDate;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static JobEntity createTestJobEntity() {
        return JobEntity.builder()
                .title("React Developer")
                .type("Full-Time")
                .description("Build and maintain scalable web applications.")
                .salary("$70K - $80K")
                .location("Remote")
                .postedDate(LocalDate.now())
                .active(true)
                .experienceLevel("Middle")
                .companyName("TechCorp Inc.")
                .companyDescription("A fast-growing technology company.")
                .companyContactEmail("hr@techcorp.com")
                .companyContactPhone("123-456-7890")
                .build();
    }

    public static JobEntity createAnotherTestJobEntity() {
        return JobEntity.builder()
                .title("Senior Java Developer")
                .type("Full-Time")
                .description("Design, develop and maintain enterprise Java applications. Mentor junior developers and lead code reviews.")
                .salary("$100K - $110K")
                .location("New York, NY (Hybrid)")
                .postedDate(LocalDate.now().minusDays(7))
                .active(true)
                .experienceLevel("Senior")
                .companyName("Innovatech Ltd.")
                .companyDescription("Innovative solutions provider specializing in scalable enterprise software.")
                .companyContactEmail("contact@innovatech.com")
                .companyContactPhone("987-654-3210")
                .build();
    }
    
    public static JobDto createTestJobDto() {
        return JobDto.builder()
                .title("React Developer")
                .type("Full-Time")
                .description("Build and maintain scalable web applications.")
                .salary("$70K - $80K")
                .location("Remote")
                .postedDate(LocalDate.now())
                .active(true)
                .experienceLevel("Middle")
                .companyName("TechCorp Inc.")
                .companyDescription("A fast-growing technology company.")
                .companyContactEmail("hr@techcorp.com")
                .companyContactPhone("123-456-7890")
                .build();
    }

    public static JobDto createAnotherTestJobDto() {
        return JobDto.builder()
                .title("Senior Java Developer")
                .type("Full-Time")
                .description("Design, develop and maintain enterprise Java applications. Mentor junior developers and lead code reviews.")
                .salary("$100K - $110K")
                .location("New York, NY (Hybrid)")
                .postedDate(LocalDate.now().minusDays(7))
                .active(true)
                .experienceLevel("Senior")
                .companyName("Innovatech Ltd.")
                .companyDescription("Innovative solutions provider specializing in scalable enterprise software.")
                .companyContactEmail("contact@innovatech.com")
                .companyContactPhone("987-654-3210")
                .build();
    }
}
