package org.nexus.eduhelp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.nexus.eduhelp.model.Ticket;

public class ControllerTicket {

    public Ticket register_ticket(Ticket t) {
        String query = "INSERT INTO Tickets (Titulo, Descripcion, Ubicacion, Categoria, Prioridad, Estado, Fecha_Creacion, Fecha_Actualizacion, Id_Alumno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, t.getTitulo());
            pstm.setString(2, t.getDescripcion());
            pstm.setString(3, t.getUbicacion());
            pstm.setString(4, t.getCategoria());
            pstm.setString(5, t.getPrioridad());
            pstm.setString(6, t.getEstado());
            pstm.setTimestamp(7, t.getFechaCreacion());
            pstm.setTimestamp(8, t.getFechaActualizacion());
            pstm.setInt(9, t.getId_Alumno());

            System.out.println("Ejecutando consulta de inserción...");
            int affectedRows = pstm.executeUpdate();
            System.out.println("Filas afectadas: " + affectedRows);

            if (affectedRows == 0) {
                throw new SQLException("La inserción del ticket falló, no se afectaron filas.");
            }

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                t.setIdTicket(rs.getInt(1));
                System.out.println("ID generado: " + rs.getInt(1));
            } else {
                throw new SQLException("La inserción del ticket falló, no se obtuvo el ID.");
            }

            rs.close();
            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return t;
    }
    
    public Ticket update_ticket(Ticket t) {
    StringBuilder query = new StringBuilder("UPDATE Tickets SET Titulo = ?, Descripcion = ?, Ubicacion = ?, Categoria = ?, Prioridad = ?, Estado = ?, Fecha_Actualizacion = ?");
    
    if (t.getId_Tecnico() != null) {
        query.append(", Id_Tecnico = ?");
    }
    
    query.append(" WHERE Id_Ticket = ?");
    
    try {
        System.out.println("Estableciendo conexión con la base de datos...");
        ConnectionMysql connMysql = new ConnectionMysql();
        Connection conn = connMysql.open();
        System.out.println("Conexión establecida.");

        PreparedStatement pstm = conn.prepareStatement(query.toString());
        pstm.setString(1, t.getTitulo());
        pstm.setString(2, t.getDescripcion());
        pstm.setString(3, t.getUbicacion());
        pstm.setString(4, t.getCategoria());
        pstm.setString(5, t.getPrioridad());
        pstm.setString(6, t.getEstado());
        pstm.setTimestamp(7, t.getFechaActualizacion());
        
        int index = 8;
        if (t.getId_Tecnico() != null) {
            pstm.setInt(index++, t.getId_Tecnico());
        }
        
        pstm.setInt(index, t.getIdTicket());

        System.out.println("Ejecutando consulta de actualización...");
        int affectedRows = pstm.executeUpdate();
        System.out.println("Filas afectadas: " + affectedRows);

        if (affectedRows == 0) {
            throw new SQLException("La actualización del ticket falló, no se afectaron filas.");
        }

        pstm.close();
        conn.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error: " + e.getMessage());
    }
    
    return t;
}

    
    public Ticket get_ticket_by_id(int idTicket) {
        String query = "SELECT * FROM Tickets WHERE Id_Ticket = ?";
        Ticket ticket = null;
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idTicket);
            
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                ticket = new Ticket(
                    rs.getInt("Id_Ticket"),
                    rs.getString("Titulo"),
                    rs.getString("Descripcion"),
                    rs.getString("Ubicacion"),
                    rs.getString("Categoria"),
                    rs.getString("Prioridad"),
                    rs.getString("Estado"),
                    rs.getTimestamp("Fecha_Creacion"),
                    rs.getTimestamp("Fecha_Actualizacion"),
                    rs.getInt("Id_Alumno"),
                    rs.getInt("Id_Tecnico")
                );
            }

            rs.close();
            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return ticket;
    }

    public List<Ticket> get_all_tickets() {
        String query = "SELECT * FROM Tickets";
        List<Ticket> tickets = new ArrayList<>();
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket(
                    rs.getInt("Id_Ticket"),
                    rs.getString("Titulo"),
                    rs.getString("Descripcion"),
                    rs.getString("Ubicacion"),
                    rs.getString("Categoria"),
                    rs.getString("Prioridad"),
                    rs.getString("Estado"),
                    rs.getTimestamp("Fecha_Creacion"),
                    rs.getTimestamp("Fecha_Actualizacion"),
                    rs.getInt("Id_Alumno"),
                    rs.getInt("Id_Tecnico")

                );
                tickets.add(ticket);
            }

            rs.close();
            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return tickets;
    }
    
    public boolean delete_ticket(int idTicket) {
        String query = "DELETE FROM Tickets WHERE Id_Ticket = ?";
        boolean deleted = false;
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idTicket);

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
    
    public List<Ticket> get_tickets_by_usuario(int idUsuario) {
        String query = "SELECT * FROM Tickets WHERE Id_Alumno = ?";
        List<Ticket> tickets = new ArrayList<>();
        
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            ConnectionMysql connMysql = new ConnectionMysql();
            Connection conn = connMysql.open();
            System.out.println("Conexión establecida.");

            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idUsuario);
            
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket(
                    rs.getInt("Id_Ticket"),
                    rs.getString("Titulo"),
                    rs.getString("Descripcion"),
                    rs.getString("Ubicacion"),
                    rs.getString("Categoria"),
                    rs.getString("Prioridad"),
                    rs.getString("Estado"),
                    rs.getTimestamp("Fecha_Creacion"),
                    rs.getTimestamp("Fecha_Actualizacion"),
                    rs.getInt("Id_Alumno"),
                    rs.getInt("Id_Tecnico")
                );
                tickets.add(ticket);
            }

            rs.close();
            pstm.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        return tickets;
    }
}
