package kr.co.ddapp.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile({"!production && !develop"})
public class PropertiesConfig {


    @Bean(name = "prop_database")
    public PropertiesFactoryBean database_mapper(){
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("database.xml"));
        return bean;
    }

}
