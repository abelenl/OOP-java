package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("dev")
class MensajeRepositoryTest {

    @Autowired
    IMensajeRepository repoSMS;
    @Autowired
    IUsuarioRepository repoUsr;
    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException {
        Usuario rte = repoUsr.seleccionar(1);
        Usuario dest = repoUsr.seleccionar(2);
        String texto = "Quedamos a las 8?";

        Mensaje nuevo = new Mensaje(null, rte, dest, texto, LocalDate.now());

        repoSMS.crear(nuevo);

        assertThat(nuevo, notNullValue());
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() {
        Usuario rte = new Usuario(null, "Ramiro", "ramiro@r.com", LocalDate.now(), true);
        Usuario dest = new Usuario(null, "Dario", "dario@d.com", LocalDate.now(), true);
        String texto = "";

        assertThrows(Exception.class, () -> {
            Mensaje nuevo = new Mensaje(null, rte, dest, texto, LocalDate.now());
            repoSMS.crear(nuevo);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException {
        Usuario rte = repoUsr.seleccionar(1);
        Usuario dest = repoUsr.seleccionar(2);

        List<Mensaje> msjs = repoSMS.obtener(rte, dest);
        MatcherAssert.assertThat(msjs, Matchers.notNullValue());
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() {
    }

}