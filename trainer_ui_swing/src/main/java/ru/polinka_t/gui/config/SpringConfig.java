package ru.polinka_t.gui.config;

import org.springframework.context.annotation.*;
import ru.polinka_t.spring.jdbc.config.DbConfig;

@Configuration
@Import(DbConfig.class)
@ComponentScan(basePackages = "ru.polinka_t")
@PropertySource("jdbc.properties")
public class SpringConfig {

}
