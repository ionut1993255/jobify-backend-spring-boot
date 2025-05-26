package dev.ionut.jobify.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "jobs")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "salary", nullable = false)
    private String salary;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "posted_date", nullable = false)
    private LocalDate postedDate;

    @Column(name = "active")
    private boolean active;

    @Column(name = "experience_level", nullable = false)
    private String experienceLevel;

    @Embedded
    private Company company;
}
