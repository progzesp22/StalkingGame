package com.progzesp.stalking.domain;

public class MessageInputEto extends com.progzesp.stalking.domain.AbstractEto{

    private Long gameId;

    private String content;

    @Override
    public Long getId() {
        return super.getId();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
