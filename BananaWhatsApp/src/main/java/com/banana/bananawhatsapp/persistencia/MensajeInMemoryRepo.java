package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MensajeInMemoryRepo implements IMensajeRepository{
    private static List<Mensaje> mensajes;

    private Usuario us1 = new Usuario(1, "Juana", "juana@e.com", LocalDate.now(), true);
    private Usuario us2 = new Usuario(2, "Luisa", "luisa@e.com", LocalDate.now(), true);

    private Integer num = 0;
    private Integer nro = 0;

    public MensajeInMemoryRepo() {
        mensajes = new ArrayList<>();
        mensajes.add(new Mensaje(1, us1, us2, "Hola, qué tal?", LocalDate.now()));
        mensajes.add(new Mensaje(2, us2, us1, "Muy bien! y tu?", LocalDate.now()));
        mensajes.add(new Mensaje(3, us1, us2, "Bien también...", LocalDate.now()));
        mensajes.add(new Mensaje(4, us2, us1, "Chachi!", LocalDate.now()));
    }

    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
        mensaje.valido();
        mensaje.setId(num + 1);
        mensajes.add(mensaje);

        return mensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario rem, Usuario dest) throws SQLException {
        List<Mensaje> sms = new ArrayList<>();
        for (Mensaje msj : mensajes) {
            if (msj.getRemitente() == rem && msj.getDestinatario() == dest){
                sms.add(msj);
            }
        }
        return sms;
    }

    @Override
    public boolean borrarTodos(Usuario rem, Usuario dest) throws SQLException {
        nro = 0;
        for (Mensaje msj : mensajes) {
            if (msj.getRemitente() == rem && msj.getDestinatario() == dest){
                mensajes.remove(msj);
                nro = nro + 1;
            }
        }

        if (nro > 0 ) return true;
        else return false;
    }
}
