package ru.omel.rm.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.omel.rm.data.entity.*;


import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DbLoadService {
    private final DogService dogService;
    private final PokService pokService;
    private final PuService puService;
    private final UserService userService;
    private final LastService lastService;
    private final PasswordEncoder passwordEncoder;
    private Last last;

    private final ContractService contractService;
    private final IndicationService indicationService;
    private final MeterDeviceService meterDeviceService;

    int count = 0;
    public DbLoadService(DogService dogService
            , PokService pokService
            , PuService puService
            , UserService userService
            , LastService lastService
            , PasswordEncoder passwordEncoder
            , ContractService contractService
            , IndicationService indicationService
            , MeterDeviceService meterDeviceService
    ) {
        this.dogService = dogService;
        this.pokService = pokService;
        this.puService = puService;
        this.userService = userService;
        this.lastService = lastService;
        this.passwordEncoder = passwordEncoder;
        this.contractService = contractService;
        this.indicationService = indicationService;
        this.meterDeviceService = meterDeviceService;
    }

    @Scheduled(fixedDelay = 1000*60*60*3)
//    @Scheduled(cron = "10 * * * 1-5 *")
//    @SchedulerLock(name = "scheduledTaskName")
    public void loadData() throws FileNotFoundException {
        if(lastService.getLast(1L).isPresent()){
            last = lastService.getLast(1L).get();
        } else {
            lastService.update(last);
        }
        loadDog();
        loadPu();
        loadPok();
        generateUsers();
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Данные загружены" + LocalDateTime.now());
    }

    private void loadPok() {
        Pok pok;
        List<String> strs;
        List<Pok> poks = new LinkedList<>();
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_pok.csv");
            if(last.getDatePok() < file.lastModified()) {
                pokService.deleteAll();
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
                //BufferedReader reader = new BufferedReader(fr);
                strs = new BufferedReader(reader)
                        .lines().collect(Collectors.toList());
                for (String s : strs) {
                    String[] as = s.split(";");
//            // data,tzona,pdate,vid_en,ce_id,ab_id
//            // 1128.406000,"",05.05.2022,"A+",31217778,2219201
                    if (as[0].equals("data")) continue;
                    pok = new Pok(as[4].trim()
                            , as[5].trim()
                            , as[3].substring(1, as[3].length() - 1).trim()
                            , as[2].trim()
                            , as[1].substring(1, as[1].length() - 1).trim()
                            , as[0].trim());
//                    pokService.update(pok);
                    poks.add(pok);
                }
                pokService.updateAll(poks);
                last.setDatePok(file.lastModified());
                lastService.update(last);
            }
            writeIndication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeIndication() throws ParseException {
        List<Pok> pokList = pokService.findAll();
        Optional<Indication> indication;
        Indication ind;
        MeterDevice mt;
        count = 0;
        for(Pok p : pokList){
            count++;
            if((meterDeviceService.findByExtId(
                        Long.valueOf(p.getCeId())))
                    .isPresent()){
                mt = (meterDeviceService.findByExtId(
                        Long.valueOf(p.getCeId()))).get();
            } else {
                continue;
            };
            indication = indicationService.
                    findByIdMeterDeviceAndDate(
                            mt
                            , new java.sql.Date(
                                    (new SimpleDateFormat("dd.MM.yyyy")
                                            .parse(p.getPdate()).getTime())));
            if(indication.isEmpty()){
                ind = new Indication();
                ind.setIdMeterDevice(mt);
                ind.setDate(new java.sql.Date(
                        (new SimpleDateFormat("dd.MM.yyyy")
                                .parse(p.getPdate()).getTime())));
            } else {
                ind = indication.get();
            }
            ind.setData(p.getData());
            ind.setTz(p.getTz());
            ind.setVidEn(p.getVidEn());
            indicationService.save(ind);
        }
    }
    private void loadDog() {
        Dog dog;
        List<String> strs;
        List<Dog> dogs = new LinkedList<>();
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_dog.csv");
            if(last.getDateDog() < file.lastModified()) {
                dogService.deleteAll();
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
                //BufferedReader reader = new BufferedReader(fr);
                strs = new BufferedReader(reader)
                        .lines().collect(Collectors.toList());
                for (String s : strs) {
                    String[] as = s.split(";");
                    // ab_numgp,ab_num,ab_name,inn,ab_id
                    // Dog(String abNum, String abNumgp, String abName, String inn, String abId)
                    if (as[0].equals("ab_numgp")) continue;
                    dog = new Dog(
                            as[1].substring(1, as[1].length() - 1).trim()
                            , as[0].substring(1, as[0].length() - 1).trim()
                            , as[2].substring(1, as[2].length() - 1).trim()
                            , as[3].trim()
                            , as[4].trim());
//                    dogService.update(dog);
                    dogs.add(dog);
                }
                dogService.updateAll(dogs);
                last.setDateDog(file.lastModified());
                lastService.update(last);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        writeContract();
    }
    private void writeContract() {
        List<Dog> dogList = dogService.findAll();
        Contract newContract;
        Optional<Long> abid; 
        for(Dog dog : dogList) {
            Optional<Contract> contract = contractService.findByStrNumber(dog.getAbNum());
            Optional<Contract> contractID = contractService.findByExtId(Long.valueOf(dog.getAbId()));
            if(contractID.isPresent()){
                if(contractID.get().getExtId() == Long.valueOf(dog.getAbId())) {
                    Contract oldContract = contractID.get();
                    oldContract.setExtId(oldContract.getExtId()*1000000);
                    contractService.save(oldContract);
                }
            }
            if(!contract.isPresent()) {
                newContract = new Contract();
                newContract.setStrNumber(dog.getAbNum());
                newContract.setStrName(dog.getAbName());
                newContract.setNumgp(dog.getAbNumgp());
                newContract.setINN(dog.getInn());
                newContract.setExtId(Long.valueOf(dog.getAbId()));
                contractService.save(newContract);
            }
        }
    }
    private void loadPu(){
        Pu pu;
        List<String> strs;
        List<Pu> pus = new LinkedList<>();
        try {
            File file = new File("\\\\omel1s.omel.corp\\in\\lk_pu.csv");
            if(last.getDatePu() < file.lastModified()) {
                puService.deleteAll();
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "windows-1251");
                //BufferedReader reader = new BufferedReader(fr);
                strs = new BufferedReader(reader)
                        .lines().collect(Collectors.toList());
                for (String s : strs) {
                    String[] as = s.split(";");
                    // 0 ob_name 3
                    // ,1 ob_adres 4
                    // ,2 nom_pu 5
                    // ,3 marka 6
                    // ,4 tn 7
                    // ,5 tt 8
                    // ,6 koef 10
                    // ,7 pr_pot 11
                    // ,8 vltl 9
                    // ,9 ce_id 2
                    // ,10 ab_id 1
                    // "Магазин "Цветы"","Центральная 4/2","47866020383720","МИР С-05","-","-",1,0.0000,"СН2",31217996,2219202

                    if (as[0].equals("ob_name")) continue;
                    pu = new Pu(as[10].trim()
                            , as[9].trim()
                            , as[0].substring(1, as[0].length() - 1).trim()
                            , as[1].substring(1, as[1].length() - 1).trim()
                            , as[2].substring(1, as[2].length() - 1).trim()
                            , as[3].substring(1, as[3].length() - 1).trim()
                            , as[4].substring(1, as[4].length() - 1).trim()
                            , as[5].substring(1, as[5].length() - 1).trim()
                            , as[8].substring(1, as[8].length() - 1).trim()
                            , as[6].trim()
                            , as[7].trim());
//                    puService.update(pu);
                    pus.add(pu);
                }
                puService.updateAll(pus);
                last.setDatePu(file.lastModified());
                lastService.update(last);
            }
            writeMeterDevice();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void writeMeterDevice(){
        List<Pu> puList = puService.findAll();
        Optional<MeterDevice> meterDevice = java.util.Optional.empty();
        MeterDevice mt;
        count = 0;
        for(Pu pu : puList){
            count++;
            try {
             meterDevice = meterDeviceService.findByExtId(
                    Long.valueOf(pu.getCeId()));
            } catch (Exception e) {
                System.out.println("ERROR on " + pu.getCeId());
            }
            if(!meterDevice.isPresent()){
                mt = new MeterDevice();
                try {
                    mt.setContract(contractService.findByExtId(
                        Long.valueOf(pu.getAbId())).get());                
                } catch (Exception e) {
                    System.out.println("ERROR on " + e.getMessage() + " pu: " + pu.getCeId());
                }
                mt.setExtId(Long.valueOf(pu.getCeId()));
            } else {
                mt = meterDevice.get();
            }
            mt.setKoef(pu.getKoef());
            mt.setMarka(pu.getMarka());
            mt.setNomPu(pu.getNomPu());
            mt.setObAdres(pu.getObAdres());
            mt.setObName(pu.getObName());
            mt.setPrPot(pu.getPrPot());
            mt.setTn(pu.getTn());
            mt.setTt(pu.getTt());
            mt.setVltlName(pu.getVltlName());
            meterDeviceService.save(mt);
        }
    }

    private void generateUsers(){
        User user;
        String name;
        List<Dog> dogs = dogService.findAll();
        for (Dog dog : dogs) {
            name = dog.getAbNum();
            user = userService.findByUsername(name);
            if(user == null) {
                user = new User();
                user.setUsername(name);
                user.setActive(true);
                user.setPassword(this.passwordEncoder.encode(revers(name)));
                user.setRoles(Collections.singleton(Role.USER));
//                user.setChangePassword(true);
                userService.update(user);
            }
        }
    }

    private String revers(String str) {
        StringBuilder newStr = new StringBuilder();
        int tail = str.length();
        for(int index = 0; index < tail; index++) {
            newStr.append(str, tail - index - 1, tail - index);
        }
        return newStr.toString();
    }
}
