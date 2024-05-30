package org.nexus.eduhelp.model;

import java.sql.Timestamp;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String tipo;
    private String contraseña;
    private int matricula;
    private Timestamp fechaCreacion;
    private Timestamp fechaActualizacion;

    public Usuario(int idUsuario, String nombre, String apellido, String correo, String tipo, String contraseña, int matricula, Timestamp fechaCreacion, Timestamp fechaActualizacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.tipo = tipo;
        this.contraseña = contraseña;
        this.matricula = matricula;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    
}
