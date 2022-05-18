package ru.omel.rm.data.entity;

import ru.omel.rm.data.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pok")
public class Pok extends AbstractEntity {
    // CE_ID
    @Column(name = "ce_id", length = 8)
    private String ceId;

    // AB_ID
    @Column(name = "ab_id", length = 8)
    private String abId;

    // PDATE
    @Column(length = 30)
    private String pdate;

    // VID_EN
    @Column(length = 2, name = "vid_en")
    private String vidEn;

    // TZONA
    @Column(length = 8)
    private String tzona;

    // DATA
    @Column(length =13)
    private String data;

    public Pok() {

    }

    public Pok(String ceId
            , String abId
            , String vidEn
            , String pdate
            , String tzona
            , String data) {
        this.abId = abId;
        this.vidEn = vidEn;
        this.tzona = tzona;
        this.pdate = pdate;
        this.ceId = ceId;
        this.data = data;
    }

    public String getCeId() {
        return ceId;
    }

    public void setCeId(String deviceId) {
        this.ceId = deviceId;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String date) {
        this.pdate = date;
    }

    public String getVidEn() {
        return vidEn;
    }

    public void setVidEn(String typeEnergy) {
        this.vidEn = typeEnergy;
    }

    public String getTzona() {
        return tzona;
    }

    public void setTzona(String tzone) {
        this.tzona = tzone;
    }

    public String getData() {
        return data;
    }
}