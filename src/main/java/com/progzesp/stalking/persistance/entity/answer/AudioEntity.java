package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer_audio")
public class AudioEntity extends NoNavPosEntity {
    @Lob
    @Basic
    @NotNull
    private byte[] audio;

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
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
