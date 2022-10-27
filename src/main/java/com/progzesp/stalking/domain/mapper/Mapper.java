package com.progzesp.stalking.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper that maps between ETOs and Entities
 * @param <A> ETO
 * @param <B> Entity
 */
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
