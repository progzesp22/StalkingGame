package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepo extends JpaRepository<AnswerEntity, Long> {

    @Query(value = "SELECT * FROM answer WHERE checked=FALSE", nativeQuery = true)
    List<AnswerEntity> findAllUncheckedAnswers();

}
