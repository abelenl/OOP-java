package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("prod")
class ServicioMensajeriaTest {

    @Autowired
    IServicioMensajeria servicioSMS;

    @Autowired
    IServicioUsuarios servicioUsr;

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() {
        Usuario rte = servicioUsr.seleccionarUsuario(1);
        Usuario dest = servicioUsr.seleccionarUsuario(2);
        String texto = "Quedamos a las 8?";

        Mensaje Msj = servicioSMS.enviarMensaje(rte, dest, texto);

        assertThat(Msj, notNullValue());
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario rte = new Usuario(null, "Ramiro", "ramiro@r.com", LocalDate.now(), true);
        Usuario dest = new Usuario(null, "Dario", "dario@d.com", LocalDate.now(), true);
        String texto = "";

        assertThrows(Exception.class, () -> {
            Mensaje Msj = servicioSMS.enviarMensaje(rte, dest, texto);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() {
        Usuario rte = servicioUsr.seleccionarUsuario(1);
        Usuario dest = servicioUsr.seleccionarUsuario(2);

        List<Mensaje> msjs = servicioSMS.mostrarChatConUsuario(rte, dest);
        MatcherAssert.assertThat(msjs, Matchers.notNullValue());
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() {
        Usuario rte = new Usuario(null, "Ramiro", "ramiro@r.com", LocalDate.now(), true);
        Usuario dest = new Usuario(null, "Dario", "dario@d.com", LocalDate.now(), true);

        assertThrows(Exception.class, () -> {
            List<Mensaje> msjs = servicioSMS.mostrarChatConUsuario(rte, dest);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() {
    }
}