package ru.omel.rm.data.service;

import ru.omel.rm.data.dto.DtoIndMet;
import ru.omel.rm.data.dto.DtoPokPu;
import ru.omel.rm.data.entity.*;

import java.util.LinkedList;
import java.util.List;

public class DataHelper {
    public static List<DtoPokPu> createTable(String num
            , DogService dogService
            , PuService puService
            , PokService pokService) {
        Dog dog;
        List<DtoPokPu> result = new LinkedList<>();

        if(dogService.findByAbNum(num).isPresent()) {
            dog = dogService.findByAbNum(num).get();
            List<Pu> pus = puService.findAllByAbId(dog.getAbId());
            for(Pu pu : pus) {
                List<Pok> poks = pokService.findAllByCeId(pu.getCeId());
                for(Pok pok : poks) {
                    DtoPokPu pokPuDto = new DtoPokPu(
                            pu.getObName()
                            , pu.getObAdres()
                            , pu.getNomPu()
                            , pu.getMarka()
                            , pu.getKoef()
                            , pok.getPdate()
                            , pok.getTz()
                            , pok.getVidEn()
                            , pok.getData()
                    );
                    result.add(pokPuDto);
                }
            }
        }
        return result;
    }

    public static List<DtoIndMet> createTableIndMet(String num
            , ContractService contractService
            , MeterDeviceService meterDeviceService
            , IndicationService indicationService) {
        Contract contract;
        List<DtoIndMet> result = new LinkedList<>();

        if(contractService.findByStrNumber(num).isPresent()) {
            contract = contractService.findByStrNumber(num).get();
            List<MeterDevice> mtList = meterDeviceService
                    .findByContract(contract);
            for(MeterDevice mt : mtList) {
                List<Indication> indications = indicationService.findByIdMeterDevice(mt);
                for(Indication ind : indications) {
                    DtoIndMet indMetDto = new DtoIndMet(
                            mt.getObName()
                            , mt.getObAdres()
                            , mt.getNomPu()
                            , mt.getMarka()
                            , mt.getKoef()
                            , ind.getDate()
                            , ind.getTz()
                            , ind.getVidEn()
                            , ind.getData()
                    );
                    result.add(indMetDto);
                }
            }
        }
        return result;
    }

}
