package ru.omel.rm.data.dto;

import java.util.Objects;

//@Entity
//@Table(name = "dto_pok_pu")
public class DtoPokPu implements Comparable { // extends AbstractEntity {
    private String objName;
    private String objAddress;
    private String typeDevice;
    private String numDevice;
    private String ratio;
    private String date;
    private String tzona;
    private String meter;

//    public DtoPokPu() {
//    }
    public DtoPokPu(String objName
            , String objAddress
            , String numDevice
            , String typeDevice
            , String ratio
            , String date
            , String tzona
            , String meter) {
        this.objName = objName;
        this.objAddress = objAddress;
        this.numDevice = numDevice;
        this.typeDevice = typeDevice;
        this.ratio = ratio;
        this.date = date;
        this.tzona = tzona;
        this.meter = meter;
    }

    public String getObjName() {
        return objName;
    }

    public String getObjAddress() {
        return objAddress;
    }

    public String getTypeDevice() {
        return typeDevice;
    }

    public String getNumDevice() {
        return numDevice;
    }

    public String getRatio() {
        return ratio;
    }

    public String getDate() {
        return date;
    }

    public String getTzona() {
        return tzona;
    }

    public String getMeter() {
        return meter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoPokPu entity = (DtoPokPu) o;
        return Objects.equals(this.objName, entity.objName) &&
                Objects.equals(this.objAddress, entity.objAddress) &&
                Objects.equals(this.numDevice, entity.numDevice) &&
                Objects.equals(this.typeDevice, entity.typeDevice) &&
                Objects.equals(this.ratio, entity.ratio) &&
                Objects.equals(this.date, entity.date) &&
                Objects.equals(this.tzona, entity.tzona) &&
                Objects.equals(this.meter, entity.meter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objName, objAddress, numDevice, typeDevice, ratio, date, tzona, meter);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "objName = " + objName + ", " +
                "objAddress = " + objAddress + ", " +
                "numDevice = " + numDevice + ", " +
                "typeDevice = " + typeDevice + ", " +
                "ratio = " + ratio +
                "date = " + date +
                "tzona = " + tzona +
                "meter = " + meter + ")";
    }

    @Override
    public int compareTo(Object o) {
        return this.numDevice.compareTo(((DtoPokPu)o).numDevice);
//        Long compare = Long.parseLong(this.numDevice) - Long.parseLong(((DtoPokPu)o).numDevice);
//        int result = (compare > 0) ? 1 : (compare < 0) ? -1 : 0;
//        return result;
    }
}