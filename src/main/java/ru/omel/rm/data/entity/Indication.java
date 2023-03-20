package ru.omel.rm.data.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DynamicUpdate
public class Indication extends AbstractEntity {
    @Column(name = "id_meter_device")
    private long idMeterDevice;

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

}
