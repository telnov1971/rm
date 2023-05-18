package ru.omel.rm.data.entity;

import ru.omel.rm.data.entity.AbstractEntity;

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
    @Column(length = 5, name = "vid_en")
    private String vidEn;

    // TZONA
    @Column(length = 8, name = "tz")
    private String tz;

    // DATA
    @Column(length =13)
    private String data;

    public Pok() {

    }

    public Pok(String ceId
            , String abId
            , String vidEn
            , String pd
            , String tz
            , String data) {
        this.abId = abId;
        this.vidEn = vidEn;
        this.tz = tz;
        this.pdate = pd;
        this.ceId = ceId;
        this.data = data;
    }

    public String getCeId() {
        return ceId;
    }

    public String getPdate() {
        return pdate;
    }

    public String getVidEn() {
        return vidEn;
    }

    public String getTz() {
        return tz;
    }

    public String getData() {
        return data;
    }

    public String getAbId() {
        return abId;
    }
}