package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepo extends JpaRepository<TeamEntity, Long> {
}
