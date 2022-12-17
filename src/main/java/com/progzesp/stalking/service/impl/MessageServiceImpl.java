package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.MessageInputEto;
import com.progzesp.stalking.domain.mapper.MessageMapper;
import com.progzesp.stalking.persistance.entity.game.GameEntity;
import com.progzesp.stalking.persistance.entity.MessageEntity;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.MessageRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Pair<Integer, List<MessageEto>> findMessagesByCriteria(Optional<Long> gameId, Optional<Long> newerThan) {
        List<MessageEntity> messages = null;
        if (gameId.isPresent()) {
            Optional<GameEntity> game = gameRepo.findById(gameId.get());
            if (game.isPresent()) {
                messages = game.get().getMessages();
                if (messages != null) {
                    if (newerThan.isPresent()) {
                        messages = messages.stream().filter(x -> x.getId() > newerThan.get()).collect(Collectors.toList());
                    }
                    return Pair.of(200, messageMapper.mapToETOList(messages));
                }
                else {
                    return Pair.of(200, messageMapper.mapToETOList(new ArrayList<>()));
                }
            }
            else {
                return Pair.of(400, new ArrayList<>());
            }
        }
        else {
            return Pair.of(400, new ArrayList<>());
        }

    }

    @Override
    public Pair<Integer, MessageEto > addMessage(MessageInputEto input, Principal user) {
        Optional<GameEntity> gameOptional = gameRepo.findById(input.getGameId());
        if (gameOptional.isEmpty()) {
            return Pair.of(400,new MessageEto());
        }
        else {
            GameEntity gameEntity = gameOptional.get();
            final Long userId = userRepo.getByUsername(user.getName()).getId();
            if (userId == gameEntity.getGameMasterId()) {
                MessageEntity messageEntity = messageMapper.mapToEntity(input);
                messageRepo.save(messageEntity);
                gameEntity.addMessage(messageEntity);
                return Pair.of(200, messageMapper.mapToETO(messageEntity));
            }
            else {
                return Pair.of(403,new MessageEto());
            }
        }
    }
}
