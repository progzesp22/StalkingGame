package com.progzesp.stalking.domain;

public class MessageEto extends com.progzesp.stalking.domain.AbstractEto{

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
