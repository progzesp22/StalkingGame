package com.progzesp.stalking.domain.answer;

import com.progzesp.stalking.domain.AnswerEto;

public class NavPosEto extends AnswerEto {
    private Coords response;

    public Coords getResponse() {
        return response;
    }

    public void setResponse(Coords response) {
        this.response = response;
    }

    public static class Coords {
        private Double lat;
        private Double lon;

        public Coords(Double lat, Double lon) {
            this.lat = lat;
            this.lon = lon;
        }

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
    }
}
