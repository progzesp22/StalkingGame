package com.progzesp.stalking.persistance.entity.answer;

import com.progzesp.stalking.domain.answer.NavPosEto;
import com.progzesp.stalking.persistance.entity.AnswerEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer_nav")
public class NavPosEntity extends AnswerEntity {
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public NavPosEto.Coords getResponseAsCoords() {
        return new NavPosEto.Coords(lat, lon);
    }

    public void setResponseFromCoords(NavPosEto.Coords response) {
        this.lat = response.getLat();
        this.lon = response.getLon();
    }

    @Override
    public boolean validate() {
        return true;
    }
}
