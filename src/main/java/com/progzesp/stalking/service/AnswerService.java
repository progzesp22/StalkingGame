package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.AnswerEto;
import org.springframework.data.util.Pair;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface AnswerService extends Service {

    Pair<Integer, AnswerEto> save(AnswerEto newTask, Principal user);

    Pair<Integer, AnswerEto> modifyAnswer(Long id, AnswerEto taskEto, Principal user);
    boolean deleteAnswer(Long id);

    Pair<Integer, AnswerEto> findAnswerById(Long id, Principal user);

    Pair<Integer, List<AnswerEto>> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter, Principal user);
}
