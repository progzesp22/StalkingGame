package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer_qr")
@DiscriminatorValue("QR")
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
        return qrCode;
    }

    @Override
    public void setResponseFromString(String response) {
        qrCode = response;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
