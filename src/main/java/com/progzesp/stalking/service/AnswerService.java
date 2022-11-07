package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.AnswerEto;

import java.util.List;

public interface AnswerService extends Service {

    AnswerEto save(AnswerEto newTask);

    List<AnswerEto> findAllUncheckedAnswers();

    List<AnswerEto> findAllAnswers();

    AnswerEto modifyAnswer(Long id, AnswerEto taskEto);
    boolean deleteAnswer(Long id);
}
