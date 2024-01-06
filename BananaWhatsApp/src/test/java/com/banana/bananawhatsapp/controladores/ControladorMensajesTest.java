package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorMensajesTest {

    @Autowired
    ControladorMensajes controladorMensajes;
    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {
        Usuario rte = controladorUsuarios.selec(1);
        Usuario dest = controladorUsuarios.selec(2);
        String texto = "Quedamos a las 8?";

        boolean ok = controladorMensajes.enviarMensaje(rte.getId(), dest.getId(), texto);

        assertThat(rte.getId(), greaterThan(0));
        assertThat(dest.getId(), greaterThan(0));
        assertThat(texto, notNullValue());
        MatcherAssert.assertThat(ok, Matchers.is(true));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario rte = new Usuario(null, "Ramiro", "ramiro@r.com", LocalDate.now(), true);
        Usuario dest = new Usuario(null, "Dario", "dario@d.com", LocalDate.now(), true);
        String texto = "";

        assertThrows(Exception.class, () -> {
            controladorMensajes.enviarMensaje(rte.getId(), dest.getId(), texto);
        });
}

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {
        Usuario rte = controladorUsuarios.selec(1);
        Usuario dest = controladorUsuarios.selec(2);

        boolean ok = controladorMensajes.mostrarChat(rte.getId(), dest.getId());
        MatcherAssert.assertThat(ok, Matchers.is(true));
}

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {
        Usuario rte = new Usuario(null, "Ramiro", "ramiro@r.com", LocalDate.now(), true);
        Usuario dest = new Usuario(null, "Dario", "dario@d.com", LocalDate.now(), true);
        String texto = "";

        assertThrows(Exception.class, () -> {
            controladorMensajes.mostrarChat(rte.getId(), dest.getId());
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {
    }
}