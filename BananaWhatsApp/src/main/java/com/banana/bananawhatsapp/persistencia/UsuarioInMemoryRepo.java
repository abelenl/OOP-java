package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.Set;
import java.util.logging.Logger;

public class UsuarioInMemoryRepo implements IUsuarioRepository {
    //private static Logger logger = Logger.getLogger("UsuarioInMemoryRepo");
    private static List<Usuario> usuarios;
    private Integer num = 0;

    public UsuarioInMemoryRepo() {
        usuarios = new ArrayList<>();

        usuarios.add(new Usuario(1, "Juana", "juana@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(2, "Luisa", "luisa@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(3, "Diana", "diana@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(4, "Pedro", "pedro@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(5, "Marco", "marco@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(6, "Ricardo", "ricardo@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(7, "Nora", "nora@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(8, "Edwin", "edwin@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(9, "Marta", "marta@e.com", LocalDate.now(), true));
        usuarios.add(new Usuario(10, "Eduardo", "eduardo@e.com", LocalDate.now(), true));
    }

    @Override
    public Usuario seleccionar(Integer id) throws SQLException {
        for (Usuario usr : usuarios) {
            if (usr.getId() == id) {
                return usr;
            }
        }
        throw new UsuarioException();
    }

    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        usuario.valido();
        usuario.setId(num + 1);
        usuarios.add(usuario);

        return usuario;
    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        for (Usuario usr : usuarios) {
            if (usr.getId() == usuario.getId()) {
                usuarios.remove(usr);
                usuarios.add(usuario);
                return usuario;
            }
        }

        throw new UsuarioException();
    }


    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
        for (Usuario usr : usuarios) {
            if (usr.getId() == usuario.getId()) {
                usuarios.remove(usr);
                return true;
            }
        }

        throw new UsuarioException();

    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        return null;
    }


}
