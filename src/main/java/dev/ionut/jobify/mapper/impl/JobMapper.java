package dev.ionut.jobify.mapper.impl;

import dev.ionut.jobify.domain.dto.JobDto;
import dev.ionut.jobify.domain.entity.JobEntity;
import dev.ionut.jobify.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JobMapper implements Mapper<JobEntity, JobDto> {

    private final ModelMapper modelMapper;

    public JobMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public JobDto mapTo(JobEntity jobEntity) {
        if (jobEntity == null) return null;

        return modelMapper.map(jobEntity, JobDto.class);
    }

    @Override
    public JobEntity mapFrom(JobDto jobDto) {
        if (jobDto == null) return null;

        return modelMapper.map(jobDto, JobEntity.class);
    }
}
