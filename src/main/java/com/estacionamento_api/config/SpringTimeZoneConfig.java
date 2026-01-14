package com.estacionamento_api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

//configuração global do TimeZone
@Configuration
public class SpringTimeZoneConfig {

    @PostConstruct //Essa anotação faz com que esse seja o primeiro metodo a ser executado apos o construtor
    public void timezoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
