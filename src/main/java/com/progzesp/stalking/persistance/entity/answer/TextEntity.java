package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer_text")
public class TextEntity extends NoNavPosEntity {
    @NotNull
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getResponseAsString() {
        return null;
    }

    @Override
    public void setResponseFromString(String response) {

    }

    @Override
    public boolean validate() {
        return true;
    }
}
