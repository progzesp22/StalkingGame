package com.progzesp.stalking.persistance.repo;

import com.progzesp.stalking.persistance.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<MessageEntity, Long> {
}
