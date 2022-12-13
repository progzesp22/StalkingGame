package com.progzesp.stalking.persistance.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class MessageEntity extends AbstractEntity{

    private String content;

    private String timestamp;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp( String timestamp) {
        this.timestamp = timestamp;
    }

}
