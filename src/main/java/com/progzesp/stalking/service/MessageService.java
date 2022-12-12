package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.MessageInputEto;
import org.springframework.data.util.Pair;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface MessageService extends Service{
    Pair<Integer, List<MessageEto>> findMessagesByCriteria(Optional<Long> gameId, Optional<Long> newerThan);

    Pair<Integer, MessageEto > addMessage(MessageInputEto input, Principal user);
}
