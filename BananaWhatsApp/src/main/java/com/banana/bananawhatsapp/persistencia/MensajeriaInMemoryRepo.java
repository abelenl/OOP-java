package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MensajeriaInMemoryRepo implements IMensajeRepository{
    Set<Mensaje> mensajes = new HashSet<>();
    private Integer num = 0;
    private Integer nro = 0;

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
