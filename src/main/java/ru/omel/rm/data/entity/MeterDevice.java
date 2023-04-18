package ru.omel.rm.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "meter_device")
public class MeterDevice extends AbstractEntity {
    // AB_ID
    @ManyToOne(fetch = FetchType.EAGER)
    private Contract contract;

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

    // CE_ID
    @Column(name= "EXT_ID")
    private Long extId;

    public MeterDevice() {
    }

    public MeterDevice(Contract contract
            , String obName
            , String obAdres
            , String nomPu
            , String marka
            , String tn
            , String tt
            , String koef
            , String prPot
            , String vltlName
            , Long extId) {
        this.contract = contract;
        this.obName = obName;
        this.obAdres = obAdres;
        this.nomPu = nomPu;
        this.marka = marka;
        this.tn = tn;
        this.tt = tt;
        this.koef = koef;
        this.prPot = prPot;
        this.vltlName = vltlName;
        this.extId = extId;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getObName() {
        return obName;
    }

    public void setObName(String obName) {
        this.obName = obName;
    }

    public String getObAdres() {
        return obAdres;
    }

    public void setObAdres(String obAdres) {
        this.obAdres = obAdres;
    }

    public String getNomPu() {
        return nomPu;
    }

    public void setNomPu(String nomPu) {
        this.nomPu = nomPu;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getKoef() {
        return koef;
    }

    public void setKoef(String koef) {
        this.koef = koef;
    }

    public String getPrPot() {
        return prPot;
    }

    public void setPrPot(String prPot) {
        this.prPot = prPot;
    }

    public String getVltlName() {
        return vltlName;
    }

    public void setVltlName(String vltlName) {
        this.vltlName = vltlName;
    }

    public Long getExtId() {
        return extId;
    }

    public void setExtId(Long extId) {
        this.extId = extId;
    }
}
