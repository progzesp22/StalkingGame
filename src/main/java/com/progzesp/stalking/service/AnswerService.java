package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.answer.AnswerEto;
import com.progzesp.stalking.domain.answer.ModifyAnswerEto;
import org.springframework.data.util.Pair;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface AnswerService extends Service {

    Pair<Integer, AnswerEto> save(AnswerEto newTask, Principal user);

    Pair<Integer, ModifyAnswerEto> modifyAnswer(Long id, ModifyAnswerEto jsonObject, Principal user);
    boolean deleteAnswer(Long id);

    Pair<Integer, AnswerEto> findAnswerById(Long id, Principal user);

    Pair<Integer, List<AnswerEto>> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter, Principal user);
}
