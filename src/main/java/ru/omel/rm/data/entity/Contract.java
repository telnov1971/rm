package ru.omel.rm.data.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contract")
public class Contract extends AbstractEntity {
    @Column(name = "num", length = 20)
    private String strNumber;

    // AB_NUMGP
    @Column(name = "numgp", length = 22)
    private String Numgp;

    // AB_NAME
    @Column(name = "name", length = 200)
    private String strName;

    // INN
    @Column(length = 12)
    private String INN;

    // AB_ID
    @Column(name = "ext_id", length = 8)
    private long extId;

    public Contract() {
    }

    public Contract(String strNumber
            , String numgp
            , String strName
            , String INN
            , long extId
    ) {
        this.strNumber = strNumber;
        Numgp = numgp;
        this.strName = strName;
        this.INN = INN;
        this.extId = extId;
    }

    public String getStrNumber() {
        return strNumber;
    }

    public void setStrNumber(String strNumber) {
        this.strNumber = strNumber;
    }

    public String getNumgp() {
        return Numgp;
    }

    public void setNumgp(String numgp) {
        Numgp = numgp;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public long getExtId() {
        return extId;
    }

    public void setExtId(long extId) {
        this.extId = extId;
    }
}
