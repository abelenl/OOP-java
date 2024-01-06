package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServicioImpl implements IServicioUsuarios {

    @Autowired
    IUsuarioRepository usuarioRepo;

    @Override
    public Usuario seleccionarUsuario(Integer id) throws UsuarioException {
        try {
            return usuarioRepo.seleccionar(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la selección: " + e.getMessage());
        }
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        try {
            usuario.valido();
            usuarioRepo.crear(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la creación: " + e.getMessage());
        }

        return usuario;
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        try {
            usuario.valido();
            usuarioRepo.borrar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsuarioException("Error en el borrado: " + e.getMessage());
        }

    return true;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        try {
            usuario.valido();
            usuarioRepo.actualizar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la actualización: " + e.getMessage());
        }

        return usuario;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Usuario usuario, int max) throws UsuarioException {
        return null;
    }
}
