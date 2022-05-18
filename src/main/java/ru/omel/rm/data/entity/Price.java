package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractDictionary;

import javax.persistence.Entity;

@Entity
public class Price extends AbstractDictionary {
    public Price() {
    }

    public Price(String name, String code) {
        super(name, code);
    }
}
