package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Base64;

@Entity
@Table(name = "answer_audio")
@DiscriminatorValue("AUDIO")
public class AudioEntity extends FileEntity {
    @Transient
    private static final byte[][] MPEG_HEADERS = {{(byte) 0x47}, {(byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0xBA},
            {(byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0xB3},
            {(byte) 0x66, (byte) 0x74, (byte) 0x79, (byte) 0x70, (byte) 0x69, (byte) 0x73, (byte) 0x6F, (byte) 0x6D}};

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
        return Base64.getEncoder().encodeToString(audio);
    }

    @Override
    public void setResponseFromString(String response) {
        audio = Base64.getDecoder().decode(response);
    }

    @Override
    public boolean validate() {
        for (byte[] header : MPEG_HEADERS) {
            if (startsWithPattern(audio, header))
                return true;
        }
        return false;
    }
}
