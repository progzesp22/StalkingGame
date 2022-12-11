package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.answer.AnswerEto;
import com.progzesp.stalking.domain.answer.ModifyAnswerEto;

import java.util.List;
import java.util.Optional;

public interface AnswerService extends Service {

    AnswerEto save(AnswerEto newTask);

    AnswerEto modifyAnswer(Long id, ModifyAnswerEto jsonObject);
    boolean deleteAnswer(Long id);

    AnswerEto findAnswerById(Long id);

    List<AnswerEto> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter);
}
