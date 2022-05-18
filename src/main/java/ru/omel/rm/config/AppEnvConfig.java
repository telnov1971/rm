package ru.omel.rm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppEnvConfig {
    @Value("${upload.path.windows:/}")
    public String uploadPathWindows;
    @Value("${upload.path.linux:/}")
    public String uploadPathLinux;
    @Bean
    public void getAppEnv() {
        String osName = System.getProperty("os.name");
        if(osName.contains("Windows")) AppEnv.setUploadPath(uploadPathWindows);
        if(osName.contains("Linux")) AppEnv.setUploadPath(uploadPathLinux);
    }
}
