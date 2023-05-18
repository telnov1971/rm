package ru.omel.rm.data.entity;

import ru.omel.rm.data.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "last")
public class Last extends AbstractEntity {
    @Column(name = "date_dog")
    long dateDog;
    @Column(name = "date_pok")
    long datePok;
    @Column(name = "date_pu")
    long datePu;

    public Last() {
    }

    public long getDateDog() {
        return dateDog;
    }

    public void setDateDog(long dateDog) {
        this.dateDog = dateDog;
    }

    public long getDatePok() {
        return datePok;
    }

    public void setDatePok(long datePok) {
        this.datePok = datePok;
    }

    public long getDatePu() {
        return datePu;
    }

    public void setDatePu(long datePu) {
        this.datePu = datePu;
    }
}