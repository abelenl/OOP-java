package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Service
public class MensajeServicioImpl implements IServicioMensajeria{
    @Autowired
    IMensajeRepository smsRepo;

    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        Mensaje msj = new Mensaje(null, remitente, destinatario, texto, LocalDate.now());
        try {
            smsRepo.crear(msj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return msj;
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        try {
            List<Mensaje> msjs = smsRepo.obtener(remitente, destinatario);

            return msjs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        try {
            smsRepo.borrarTodos(remitente, destinatario);

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
