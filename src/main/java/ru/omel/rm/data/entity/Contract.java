package ru.omel.rm.data.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DynamicUpdate
public class Contract extends AbstractEntity {
    @Column(name = "num", length = 20)
    private String Num;

    // AB_NUMGP
    @Column(name = "numgp", length = 22)
    private String Numgp;

    // AB_NAME
    @Column(name = "name", length = 200)
    private String Name;

    // INN
    @Column(length = 12)
    private String INN;

    // AB_ID
//    @Column(name = "ext_id", length = 8)
//    private long extId;

    public Contract() {
    }

    public Contract(String num
            , String numgp
            , String name
            , String INN
//            , long extId
    ) {
        Num = num;
        Numgp = numgp;
        Name = name;
        this.INN = INN;
//        this.extId = extId;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getNumgp() {
        return Numgp;
    }

    public void setNumgp(String numgp) {
        Numgp = numgp;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

//    public long getExtId() {
//        return extId;
//    }
//
//    public void setExtId(long extId) {
//        this.extId = extId;
//    }
}
