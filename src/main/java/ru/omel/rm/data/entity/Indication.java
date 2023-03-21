package ru.omel.rm.data.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DynamicUpdate
public class Indication extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_device")
    private MeterDevice idMeterDevice;

    // AB_ID
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contract")
    private Contract idContract;

    // PDATE
    private Date date;

    // VID_EN
    @Column(length = 5, name = "vid_en")
    private String vidEn;

    // TZONA
    @Column(length = 8, name = "tz")
    private String tz;

    // DATA
    @Column(length =13)
    private String data;

    public Indication() {
    }

    public Indication(MeterDevice idMeterDevice
            , Contract idContract
            , Date date
            , String vidEn
            , String tz
            , String data) {
        this.idMeterDevice = idMeterDevice;
        this.idContract = idContract;
        this.date = date;
        this.vidEn = vidEn;
        this.tz = tz;
        this.data = data;
    }

    public MeterDevice getIdMeterDevice() {
        return idMeterDevice;
    }

    public void setIdMeterDevice(MeterDevice idMeterDevice) {
        this.idMeterDevice = idMeterDevice;
    }

    public Contract getIdContract() {
        return idContract;
    }

    public void setIdContract(Contract idContract) {
        this.idContract = idContract;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVidEn() {
        return vidEn;
    }

    public void setVidEn(String vidEn) {
        this.vidEn = vidEn;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
