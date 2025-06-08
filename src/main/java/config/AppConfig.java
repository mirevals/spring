package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${students.csv.path}")
    private String studentsCsvPath;

    @Bean
    public Resource studentsResource() {
        return new ClassPathResource(studentsCsvPath);
    }
}
