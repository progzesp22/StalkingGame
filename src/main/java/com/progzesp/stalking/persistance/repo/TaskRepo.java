package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<TaskEntity, Long> {
}
