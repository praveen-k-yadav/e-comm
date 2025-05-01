package com.ecommerce.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//Below is class of modemapper library that is used transform one type of object to another ,or we can say help in object mapping.
//Here the returned object is registered as a bean which will be injcted in other components of the project.
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
