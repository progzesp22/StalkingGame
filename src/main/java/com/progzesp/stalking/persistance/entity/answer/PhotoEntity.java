package com.progzesp.stalking.persistance.entity.answer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "answer_photo")
public class PhotoEntity extends FileEntity {
    @Transient
    private static final byte[] JPEG_HEADER = new byte[] {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
    @Transient
    private static final byte[] PNG_HEADER = new byte[] {
            (byte) 0x89,
            'P', 'N', 'G',
            (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};
    @Transient
    private static final byte[] GIF_HEADER_87A = "GIF87a".getBytes(StandardCharsets.US_ASCII);
    @Transient
    private static final byte[] GIF_HEADER_89A = "GIF89a".getBytes(StandardCharsets.US_ASCII);

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
        return new String(photo, StandardCharsets.UTF_8);
    }

    @Override
    public void setResponseFromString(String response) {
        photo = response.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean validate() {
        return startsWithPattern(photo, JPEG_HEADER) || startsWithPattern(photo, PNG_HEADER)
                || startsWithPattern(photo, GIF_HEADER_87A) || startsWithPattern(photo, GIF_HEADER_89A);
    }
}
