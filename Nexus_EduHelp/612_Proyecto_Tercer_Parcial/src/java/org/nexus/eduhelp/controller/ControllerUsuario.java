package org.nexus.eduhelp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.nexus.eduhelp.model.Usuario;

public class ControllerUsuario {

    public Usuario register_user(Usuario u) {
        if (!isValidUserType(u.getTipo())) {
            System.out.println("Tipo de usuario no válido: " + u.getTipo());
            throw new IllegalArgumentException("Tipo de usuario no válido: " + u.getTipo());
        }

        String query = "INSERT INTO Usuarios (Nombre, Apellido, Correo, Tipo, Contraseña, Matricula, Fecha_Creacion, Fecha_Actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, u.getNombre());
            pstm.setString(2, u.getApellido());
            pstm.setString(3, u.getCorreo());
            pstm.setString(4, u.getTipo());
            pstm.setString(5, u.getContraseña());
            pstm.setInt(6, u.getMatricula());
            pstm.setTimestamp(7, u.getFechaCreacion());
            pstm.setTimestamp(8, u.getFechaActualizacion());

            System.out.println("Ejecutando consulta de inserción...");
            int affectedRows = pstm.executeUpdate();
            System.out.println("Filas afectadas: " + affectedRows);

            if (affectedRows == 0) {
                throw new SQLException("La inserción del usuario falló, no se afectaron filas.");
            }

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                u.setIdUsuario(rs.getInt(1));
                System.out.println("ID generado: " + rs.getInt(1));
            } else {
                throw new SQLException("La inserción del usuario falló, no se obtuvo el ID.");
            }

            rs.close();
            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return u;
    }
    
    public Usuario update_user(Usuario u) {
        if (!isValidUserType(u.getTipo())) {
            System.out.println("Tipo de usuario no válido: " + u.getTipo());
            throw new IllegalArgumentException("Tipo de usuario no válido: " + u.getTipo());
        }

        String query = "UPDATE Usuarios SET Nombre = ?, Apellido = ?, Correo = ?, Tipo = ?, Contraseña = ?, Matricula = ?, Fecha_Actualizacion = ? WHERE Id_Usuario = ?";
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, u.getNombre());
            pstm.setString(2, u.getApellido());
            pstm.setString(3, u.getCorreo());
            pstm.setString(4, u.getTipo());
            pstm.setString(5, u.getContraseña());
            pstm.setInt(6, u.getMatricula());
            pstm.setTimestamp(7, u.getFechaActualizacion());
            pstm.setInt(8, u.getIdUsuario());

            System.out.println("Ejecutando consulta de actualización...");
            int affectedRows = pstm.executeUpdate();
            System.out.println("Filas afectadas: " + affectedRows);

            if (affectedRows == 0) {
                throw new SQLException("La actualización del usuario falló, no se afectaron filas.");
            }

            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return u;
    }
    
    
    public Usuario get_user_by_id(int idUsuario) {
        String query = "SELECT * FROM Usuarios WHERE Id_Usuario = ?";
        Usuario user = null;
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idUsuario);
            
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                user = new Usuario(
                    rs.getInt("Id_Usuario"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Correo"),
                    rs.getString("Tipo"),
                    rs.getString("Contraseña"),
                    rs.getInt("Matricula"),
                    rs.getTimestamp("Fecha_Creacion"),
                    rs.getTimestamp("Fecha_Actualizacion")
                );
            }

            rs.close();
            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return user;
    }

    public List<Usuario> get_all_users() {
        String query = "SELECT * FROM Usuarios";
        List<Usuario> users = new ArrayList<>();
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Usuario user = new Usuario(
                    rs.getInt("Id_Usuario"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Correo"),
                    rs.getString("Tipo"),
                    rs.getString("Contraseña"),
                    rs.getInt("Matricula"),
                    rs.getTimestamp("Fecha_Creacion"),
                    rs.getTimestamp("Fecha_Actualizacion")
                );
                users.add(user);
            }

            rs.close();
            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return users;
    }
    
    public boolean delete_user(int idUsuario) {
        String query = "DELETE FROM Usuarios WHERE Id_Usuario = ?";
        boolean deleted = false;
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idUsuario);

            System.out.println("Ejecutando consulta de eliminación...");
            int affectedRows = pstm.executeUpdate();
            System.out.println("Filas afectadas: " + affectedRows);

            if (affectedRows > 0) {
                deleted = true;
            }

            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return deleted;
    }
    
    private static final String[] VALID_USER_TYPES = {"Admin", "Alumno", "Técnico"};

    private boolean isValidUserType(String tipo) {
        for (String validType : VALID_USER_TYPES) {
            if (validType.equals(tipo)) {
                return true;
            }
        }
        return false;
    }
}
