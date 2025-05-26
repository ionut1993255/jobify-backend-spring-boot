package dev.ionut.jobify.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Company {

    @Column(name = "company_name", nullable = false)
    private String name;

    @Column(name = "company_description", nullable = false)
    private String description;

    @Column(name = "company_contact_email", nullable = false)
    private String contactEmail;

    @Column(name = "company_contact_phone", nullable = false)
    private String contactPhone;
}
