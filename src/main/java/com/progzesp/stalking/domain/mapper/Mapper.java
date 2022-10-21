package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.TaskEntity;

import java.util.List;

public interface Mapper<A, B> {

    //TODO: check other options of mapping, example: ModelMapper

    A mapToETO(B entity);

    B mapToEntity(A To);

    List<A> mapToETOList(List<B> entities);

    List<B> mapToEntityList(List<A> tos);
}
