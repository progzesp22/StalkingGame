package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<A, B> {

    A mapToETO(B entity);

    B mapToEntity(A To);

    default List<A> mapToETOList(List<B> entities) {
        return entities.stream().map(this::mapToETO).collect(Collectors.toList());
    }

    default List<B> mapToEntityList(List<A> tos) {
        return tos.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}
