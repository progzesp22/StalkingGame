package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByGame_Id(Long id);


}
