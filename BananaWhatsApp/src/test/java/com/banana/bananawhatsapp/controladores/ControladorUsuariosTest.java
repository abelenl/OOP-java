package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorUsuariosTest {

    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario nuevo = new Usuario(null, "Ricard", "ric2@r.com", LocalDate.now(), true);
        controladorUsuarios.alta(nuevo);

        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() throws Exception{
        Usuario nuevo = new Usuario(null, "F", "f.com", LocalDate.now(), true);
        Assertions.assertThrows(Exception.class, () ->{
            controladorUsuarios.alta(nuevo);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
        Usuario usold = new Usuario(null, "Jose1", "jose@jj.com", LocalDate.now(), true);
        Usuario usnew = usold;
        String eNuevo = "Jose1@jj1.es";

        controladorUsuarios.alta(usold);
        usnew.setEmail(eNuevo);
        controladorUsuarios.actualizar(usnew);
        assertEquals(usold.getEmail(), eNuevo);
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        Usuario user = null;
        assertThrows(Exception.class, () -> {
            controladorUsuarios.actualizar(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() {
        int id = 16;
        boolean ok = controladorUsuarios.baja(controladorUsuarios.selec(id));
        MatcherAssert.assertThat(ok, Matchers.is(true));
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        int id = 100;
            assertThrows(Exception.class, () -> {
            boolean ok = controladorUsuarios.baja(controladorUsuarios.selec(id));
        });
    }
}