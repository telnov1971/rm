package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractDictionary;

import javax.persistence.Entity;

@Entity
public class Plan extends AbstractDictionary {
    public Plan() {
    }

    public Plan(String name, String code) {
        super(name, code);
    }
}
