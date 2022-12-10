package com.progzesp.stalking.service;

import com.google.gson.JsonObject;
import com.progzesp.stalking.domain.AnswerEto;

import java.util.List;
import java.util.Optional;

public interface AnswerService extends Service {

    AnswerEto save(AnswerEto newTask);

    AnswerEto modifyAnswer(Long id, JsonObject jsonObject);
    boolean deleteAnswer(Long id);

    AnswerEto findAnswerById(Long id);

    List<AnswerEto> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter);
}
