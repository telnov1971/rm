package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractDictionary;

import javax.persistence.Entity;

@Entity
public class Voltage extends AbstractDictionary {
    private Boolean optional;
    public Voltage() {
        this.optional = false;
    }

    public Voltage(String name, String code) {
        super(name, code);
        this.optional = false;
    }

    public Boolean getOptional() {
        return optional;
    }
    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}
