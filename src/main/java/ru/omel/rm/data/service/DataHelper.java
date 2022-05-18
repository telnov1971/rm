package ru.omel.rm.data.service;

import ru.omel.rm.data.dto.DtoPokPu;
import ru.omel.rm.data.entity.Dog;
import ru.omel.rm.data.entity.Pok;
import ru.omel.rm.data.entity.Pu;

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
                            , pu.getMarka()
                            , pu.getNomPu()
                            , pu.getKoef()
                            , pok.getPdate()
                            , pok.getData()
                    );
                    result.add(pokPuDto);
                }
            }
        }
        return result;
    }
}
