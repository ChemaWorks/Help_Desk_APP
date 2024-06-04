package org.nexus.eduhelp.controller;

import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.SQLException;

public class ConnectionMysql {
    private Connection conn;
    
    public Connection open() {
        String user = "root";
        String password = "Moises2022";
        String url = "jdbc:mysql://127.0.0.1:3306/HELPDESK_SALLE";
        String parametros = "?useSSL=false&useUnicode=true&characterEncoding=utf-8";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url + parametros, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
