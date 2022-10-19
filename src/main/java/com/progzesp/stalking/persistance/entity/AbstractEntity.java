package com.progzesp.stalking.persistance.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity extends AbstractPersistable<Long> implements Serializable {
}