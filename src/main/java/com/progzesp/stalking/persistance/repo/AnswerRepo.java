package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<AnswerEntity, Long>, CustomAnswerRepo {


}
