package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import lombok.*;

@Setter
@Getter
public class MensajeriaJDBCRepo implements IMensajeRepository{
    private String db_url;
    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
        String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            mensaje.valido();

            stmt.setString(1, mensaje.getCuerpo());
            stmt.setDate(2, Date.valueOf(mensaje.getFecha()));
            stmt.setInt(3, mensaje.getRemitente().getId());
            stmt.setInt(4, mensaje.getDestinatario().getId());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("No id asignado");
            }
        } catch (MensajeException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        return mensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario rem, Usuario dest) throws SQLException {
        List<Mensaje> mensajes = new ArrayList<>();
        String sql = "SELECT * FROM mensaje msj WHERE msj.from_user=? AND msj.to_user=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);

        ) {
            stmt.setInt(1, rem.getId());
            stmt.setInt(2, dest.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mensajes.add(new Mensaje(rs.getInt("id"), rem, dest, rs.getString("cuerpo"),
                        rs.getDate("fecha").toLocalDate()));
            }
        }
        return mensajes;
    }

    @Override
    public boolean borrarTodos(Usuario rem, Usuario dest) throws SQLException {
        String sql = "DELETE FROM mensaje msj WHERE msj.from_user=? AND msj.to_user=?";
        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, rem.getId());
            stmt.setInt(2, dest.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if(rows<=0){
                throw new MensajeException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }
}
