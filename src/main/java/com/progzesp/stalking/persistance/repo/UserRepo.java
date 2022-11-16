package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
