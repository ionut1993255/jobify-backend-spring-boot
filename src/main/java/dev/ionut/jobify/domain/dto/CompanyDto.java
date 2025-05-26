package dev.ionut.jobify.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {

    private String name;

    private String description;

    private String contactEmail;

    private String contactPhone;
}

