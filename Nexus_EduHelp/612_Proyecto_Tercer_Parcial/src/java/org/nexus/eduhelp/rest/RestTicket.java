package org.nexus.eduhelp.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;
import org.nexus.eduhelp.controller.ControllerTicket;
import org.nexus.eduhelp.model.Ticket;

@Path("ticket")
public class RestTicket {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("register_ticket")
    public Response save(@QueryParam("titulo") String titulo, 
                         @QueryParam("descripcion") String descripcion,
                         @QueryParam("prioridad") String prioridad,
                         @QueryParam("estado") String estado,
                         @QueryParam("idUsuario") int idUsuario) {
        
        String out = "";
        
        try {
            Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
            Timestamp fechaActualizacion = fechaCreacion;
            
            Ticket ticket = new Ticket(0, titulo, descripcion, prioridad, estado, fechaCreacion, fechaActualizacion, idUsuario);
            ControllerTicket controllerTicket = new ControllerTicket();
            controllerTicket.register_ticket(ticket);
            out = "{\"response\":\"Insert Correct\"}\n";
            
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error to insert: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
    
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update_ticket")
    public Response update(@QueryParam("idTicket") int idTicket, 
                           @QueryParam("titulo") String titulo, 
                           @QueryParam("descripcion") String descripcion,
                           @QueryParam("prioridad") String prioridad,
                           @QueryParam("estado") String estado,
                           @QueryParam("idUsuario") int idUsuario) {
        
        String out = "";
        
        try {
            Timestamp fechaActualizacion = new Timestamp(System.currentTimeMillis());
            
            Ticket ticket = new Ticket(idTicket, titulo, descripcion, prioridad, estado, null, fechaActualizacion, idUsuario);
            ControllerTicket controllerTicket = new ControllerTicket();
            controllerTicket.update_ticket(ticket);
            out = "{\"response\":\"Update Correct\"}\n";
            
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error to update: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get_ticket")
    public Response getTicketById(@QueryParam("idTicket") int idTicket) {
        String out = "";
        
        try {
            ControllerTicket controllerTicket = new ControllerTicket();
            Ticket ticket = controllerTicket.get_ticket_by_id(idTicket);
            if (ticket != null) {
                out = new Gson().toJson(ticket);
            } else {
                out = "{\"response\":\"Ticket no encontrado\"}\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error al obtener ticket: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get_all_tickets")
    public Response getAllTickets() {
        String out = "";
        
        try {
            ControllerTicket controllerTicket = new ControllerTicket();
            List<Ticket> tickets = controllerTicket.get_all_tickets();
            out = new Gson().toJson(tickets);
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error al obtener tickets: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete_ticket")
    public Response deleteTicketById(@QueryParam("idTicket") int idTicket) {
        String out = "";
        
        try {
            ControllerTicket controllerTicket = new ControllerTicket();
            boolean deleted = controllerTicket.delete_ticket(idTicket);
            if (deleted) {
                out = "{\"response\":\"Ticket eliminado correctamente\"}\n";
            } else {
                out = "{\"response\":\"Ticket no encontrado\"}\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error al eliminar ticket: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
}
