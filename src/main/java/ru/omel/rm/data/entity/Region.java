package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractDictionary;

import javax.persistence.Entity;

@Entity
public class Region extends AbstractDictionary {
    public Region() {
    }

    public Region(String name, String code) {
        super(name, code);
    }
}
