package dev.ionut.jobify.repository;

import dev.ionut.jobify.domain.entity.JobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<JobEntity, Integer> {
}
