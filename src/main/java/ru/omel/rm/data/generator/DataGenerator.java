package ru.omel.rm.data.generator;

import ru.omel.rm.data.entity.*;
import ru.omel.rm.data.service.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.util.Collections;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if(userRepository.count() != 0L) {
                logger.info("Using existing 'Demand' table");
                return;
            } else {
                try {
                    userRepository.save(
                            new User("user", "",true, "User", "666-666", Collections.singleton(Role.USER)));
                    userRepository.save(
                            new User("admin", "",true, "Admin", "007", Collections.singleton(Role.USER)));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Demand entities...");

            String listDemander[] = {"Ivanov","Petrov","Sidorov","Kozlov","Kalashnikov",
                    "Kuznetsov","Semenov","Savaliev","Arbatov","Nemo"};
            String listIssued[] = {"Moskow","Omsk","Piter","Sochi","Tambov",
                    "Anapa","Khabarovsk","Krasnoyarsk","Ufa","Tumen"};
            String listObject[] = {"House","Home","Office","Enterprise","Factory","Hospital","Restaurant"};
            String listNumber[] = {"123456","654321","153624","452163","145236"};
            String listSeries[] = {"1234","6543","1536","4521","1452"};

            logger.info("Generated demo data");
        };
    }
}