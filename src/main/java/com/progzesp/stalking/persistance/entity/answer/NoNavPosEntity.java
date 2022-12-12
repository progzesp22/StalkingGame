package com.progzesp.stalking.persistance.entity.answer;

public abstract class NoNavPosEntity extends AnswerEntity {
    public abstract String getResponseAsString();

    public abstract void setResponseFromString(String response);
}
