package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractDictionary;

import javax.persistence.Entity;

@Entity
public class Safety extends AbstractDictionary {
    public Safety() {
    }

    public Safety(String name, String code) {
        super(name, code);
    }
}
