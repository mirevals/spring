package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"repository", "service", "security", "app", "model", "controller"})
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class AppConfig {
}
