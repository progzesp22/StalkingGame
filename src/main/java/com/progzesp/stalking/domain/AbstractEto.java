package com.progzesp.stalking.domain;

/**
 * Abstract Entity transfer object.
 * Request body is incoming in this form.
 * Response is sent in this form.
 * Allows to not send the whole entity and improves safety.
 */
public abstract class AbstractEto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
