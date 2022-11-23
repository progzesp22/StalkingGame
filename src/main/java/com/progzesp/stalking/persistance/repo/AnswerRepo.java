package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.AnswerEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepo extends JpaRepository<AnswerEntity, Long> {

    List<AnswerEntity> findByChecked(boolean checked);


    List<AnswerEntity> findByGame_Id(Long id);

    List<AnswerEntity> findByGame_IdAndChecked(Long id, boolean checked);





}
