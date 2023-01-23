package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomAnswerRepo {

    List<AnswerEntity> findAnswersByCriteria(Optional<Long> gameId, Optional<Boolean> checked);

    List<AnswerEntity> findAnswersByCriteria(Optional<Long> gameId, Optional<Boolean> checked, UserEntity user);
}
