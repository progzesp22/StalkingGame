package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.MessageEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<MessageEntity, Long> {
}
