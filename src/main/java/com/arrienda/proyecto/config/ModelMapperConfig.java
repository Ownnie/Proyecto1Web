package com.arrienda.proyecto.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Custom converter for Date
        modelMapper.addConverter(context -> {
            String source = context.getSource();
            if (source == null) {
                return null;
            }
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(source);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, String.class, Date.class);

        return modelMapper;
    }
}
