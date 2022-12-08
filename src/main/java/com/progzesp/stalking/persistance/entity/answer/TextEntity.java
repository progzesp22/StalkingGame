package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer_text")
@DiscriminatorValue("TEXT")
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
        return text;
    }

    @Override
    public void setResponseFromString(String response) {
        text = response;
    }

    @Override
    public boolean validate() {
        return text.length() <= 500;
    }
}
