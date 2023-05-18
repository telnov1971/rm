package ru.omel.rm.data.entity;

import ru.omel.rm.data.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pu")
public class Pu extends AbstractEntity {
    // AB_ID
    @Column(name = "ab_id", length = 8)
    private String abId;

    // CE_ID
    @Column(name= "ce_id", length = 8)
    private String ceId;

    // OB_NAME
    @Column(name= "ob_name", length = 250)
    private String obName;

    // OB_ADRES
    @Column(name= "ob_address", length = 130)
    private String obAdres;

    // NOM_PU
    @Column(name= "nom_pu", length = 20)
    private String nomPu;

    // MARKA
    @Column(name= "marka", length = 30)
    private String marka;

    // TN
    @Column(name= "tn", length = 10)
    private String tn;

    // TT
    @Column(name= "tt", length = 10)
    private String tt;

    // KOEF
    @Column(length = 6)
    private String koef;

    // PR_POT
    @Column(name= "los_per", length = 6)
    private String prPot;

    // VLTL_NAME
    @Column(length = 10)
    private String vltlName;


    public Pu() {
    }

    public Pu(String abId
            , String ceId
            , String obName
            , String obAdres
            , String nomPu
            , String marka
            , String tn
            , String tt
            , String vltlName
            , String koef
            , String prPot) {
        this.abId = abId;
        this.obName = obName;
        this.obAdres = obAdres;
        this.nomPu = nomPu;
        this.marka = marka;
        this.tn = tn;
        this.tt = tt;
        this.koef = koef;
        this.prPot = prPot;
        this.vltlName = vltlName;
        this.ceId = ceId;
    }

    public String getAbId() {
        return abId;
    }

    public void setAbId(String contractId) {
        this.abId = contractId;
    }

    public String getCeId() {
        return ceId;
    }

    public void setCeId(String devId) {
        this.ceId = devId;
    }

    public String getObName() {
        return obName;
    }

    public void setObName(String objName) {
        this.obName = objName;
    }

    public String getObAdres() {
        return obAdres;
    }

    public void setObAdres(String objAddress) {
        this.obAdres = objAddress;
    }

    public String getNomPu() {
        return nomPu;
    }

    public void setNomPu(String numDevice) {
        this.nomPu = numDevice;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String typeDevice) {
        this.marka = typeDevice;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String trU) {
        this.tn = trU;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String trI) {
        this.tt = trI;
    }

    public String getKoef() {
        return koef;
    }

    public void setKoef(String ratio) {
        this.koef = ratio;
    }

    public String getPrPot() {
        return prPot;
    }

    public void setPrPot(String lossesPercent) {
        this.prPot = lossesPercent;
    }

    public String getVltlName() {
        return vltlName;
    }

    public void setVltlName(String voltage) {
        this.vltlName = voltage;
    }
}