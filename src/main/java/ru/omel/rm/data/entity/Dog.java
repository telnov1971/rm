package ru.omel.rm.data.entity;

import ru.omel.rm.data.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dog")
public class Dog extends AbstractEntity {
    // AB_NUM
    @Column(name = "ab_num", length = 20)
    private String abNum;

    // AB_NUMGP
    @Column(name = "ab_numgp", length = 22)
    private String abNumgp;

    // AB_NAME
    @Column(name = "ab_name", length = 200)
    private String abName;

    // INN
    @Column(length = 12)
    private String inn;

    // AB_ID
    @Column(name = "ab_id", length = 8)
    private String abId;

    public Dog() {

    }

    public Dog(String abNum, String abNumgp, String abName, String inn, String abId) {
        this.abNum = abNum;
        this.abNumgp = abNumgp;
        this.abName = abName;
        this.inn = inn;
        this.abId = abId;
    }

    public String getAbNum() {
        return abNum;
    }

    public void setAbNum(String num) {
        this.abNum = num;
    }

    public String getAbNumgp() {
        return abNumgp;
    }

    public void setAbNumgp(String numGP) {
        this.abNumgp = numGP;
    }

    public String getAbName() {
        return abName;
    }

    public void setAbName(String name) {
        this.abName = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getAbId() {
        return abId;
    }

    public void setAbId(String abId) {
        this.abId = abId;
    }
}