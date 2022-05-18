package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractDictionary;

import javax.persistence.Entity;

@Entity
public class Garant extends AbstractDictionary {
    public Garant() {
    }

    public Garant(String name, String code) {
        super(name, code);
    }
}
