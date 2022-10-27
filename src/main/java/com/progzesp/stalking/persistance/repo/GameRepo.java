package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepo extends JpaRepository<GameEntity, Long> {

}
