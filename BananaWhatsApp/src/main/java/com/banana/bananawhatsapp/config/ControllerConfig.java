package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.controladores.ControladorMensajes;
import com.banana.bananawhatsapp.controladores.ControladorUsuarios;
import com.banana.bananawhatsapp.servicios.IServicioMensajeria;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ControllerConfig {

    @Autowired
    IServicioUsuarios userSvc;

    @Autowired
    IServicioMensajeria smsSvc;

    @Bean
    @Profile("prod")
    ControladorUsuarios crearUsuarioController() {
        ControladorUsuarios controller = new ControladorUsuarios();
        controller.setServicioUsuarios(userSvc);
        return controller;
    }

    @Bean
    @Profile("prod")
    ControladorMensajes crearMensajesController() {
        ControladorMensajes controller = new ControladorMensajes();
        controller.setServicioMensajeria(smsSvc);
        return controller;
    }

}
