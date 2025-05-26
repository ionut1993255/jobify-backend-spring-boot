package dev.ionut.jobify.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDto {

    private Integer id;

    private String title;

    private String type;

    private String description;

    private String salary;

    private String location;

    private LocalDate postedDate;

    private boolean active;

    private String experienceLevel;

    private CompanyDto companyDto;
}
