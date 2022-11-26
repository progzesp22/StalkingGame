package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer_photo")
public class PhotoEntity extends NoNavPosEntity {
    @Lob
    @Basic
    @NotNull
    private byte[] photo;

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
