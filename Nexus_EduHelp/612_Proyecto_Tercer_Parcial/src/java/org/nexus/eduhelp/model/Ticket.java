package org.nexus.eduhelp.model;

import java.sql.Timestamp;

public class Ticket {
    private int idTicket;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String Categoria;
    private String prioridad;
    private String Estado;
    private Timestamp fechaCreacion;
    private Timestamp fechaActualizacion;
    private int Id_Alumno;
    private Integer Id_Tecnico;

    public Ticket(int idTicket, String titulo, String descripcion, String ubicacion, String Categoria, String prioridad, String Estado, Timestamp fechaCreacion, Timestamp fechaActualizacion, int Id_Alumno, Integer Id_Tecnico) {
        this.idTicket = idTicket;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.Categoria = Categoria;
        this.prioridad = prioridad;
        this.Estado = Estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.Id_Alumno = Id_Alumno;
        this.Id_Tecnico = Id_Tecnico;
    }

   

    public Ticket() {
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
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

    public int getId_Alumno() {
        return Id_Alumno;
    }

    public void setId_Alumno(int Id_Alumno) {
        this.Id_Alumno = Id_Alumno;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public Integer getId_Tecnico() {
        return Id_Tecnico;
    }

    public void setId_Tecnico(Integer Id_Tecnico) {
        this.Id_Tecnico = Id_Tecnico;
    }
    
    
    
}
