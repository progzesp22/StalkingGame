package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer_qr")
public class QREntity extends NoNavPosEntity {
    @NotNull
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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
