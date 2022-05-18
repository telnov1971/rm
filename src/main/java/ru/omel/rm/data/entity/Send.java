package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractDictionary;

import javax.persistence.Entity;

@Entity
public class Send extends AbstractDictionary {
    public Send() {
    }

    public Send(String name, String code) {
        super(name, code);
    }
}
