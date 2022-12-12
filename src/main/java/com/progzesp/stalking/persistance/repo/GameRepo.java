package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<GameEntity, Long> {

}
