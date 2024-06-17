package org.nexus.eduhelp.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import org.nexus.eduhelp.controller.ControllerTicket;
import org.nexus.eduhelp.controller.ControllerUsuario;
import org.nexus.eduhelp.model.Ticket;
import org.nexus.eduhelp.model.Usuario;

@Path("usuario")
public class RestUsuario {

    private static final int BASE_MATRICULA = 70000;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("register_user")
    public Response save(@QueryParam("nombre") String nombre, 
                         @QueryParam("apellido") String apellido,
                         @QueryParam("correo") String correo,
                         @QueryParam("tipo") String tipo,
                         @QueryParam("contraseña") String contraseña){
        
        String out = "";
        
        try {
            // Decodificar los parámetros
            nombre = URLDecoder.decode(nombre, StandardCharsets.UTF_8.name());
            apellido = URLDecoder.decode(apellido, StandardCharsets.UTF_8.name());
            correo = URLDecoder.decode(correo, StandardCharsets.UTF_8.name());
            tipo = URLDecoder.decode(tipo, StandardCharsets.UTF_8.name());
            contraseña = URLDecoder.decode(contraseña, StandardCharsets.UTF_8.name());

            ControllerUsuario controllerUser = new ControllerUsuario();

            // Obtener la última matrícula y asignar la siguiente
            int ultimaMatricula = controllerUser.getUltimaMatricula();
            int nuevaMatricula = (ultimaMatricula < BASE_MATRICULA) ? BASE_MATRICULA : ultimaMatricula + 1;

            Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
            Timestamp fechaActualizacion = fechaCreacion;
            
            Usuario user = new Usuario(0, nombre, apellido, correo, tipo, contraseña, nuevaMatricula, fechaCreacion, fechaActualizacion);
            controllerUser.register_user(user);
            out = "{\"response\":\"Insert Correct\"}\n";
            
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error to insert: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update_user")
    public Response update(@QueryParam("idUsuario") int idUsuario, 
                           @QueryParam("nombre") String nombre, 
                           @QueryParam("apellido") String apellido,
                           @QueryParam("correo") String correo,
                           @QueryParam("tipo") String tipo,
                           @QueryParam("contraseña") String contraseña,
                           @QueryParam("matricula") int matricula) {
        
        String out = "";
        
        try {
            
            Timestamp fechaActualizacion = new Timestamp(System.currentTimeMillis());
            
            Usuario user = new Usuario(idUsuario, nombre, apellido, correo, tipo, contraseña, matricula, null, fechaActualizacion);
            ControllerUsuario controllerUser = new ControllerUsuario();
            controllerUser.update_user(user);
            out = "{\"response\":\"Update Correct\"}\n";
            
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error to update: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
    
    
    @GET
@Produces(MediaType.APPLICATION_JSON)
@Path("get_user")
public Response getUserById(@QueryParam("idUsuario") int idUsuario) {
    String out = "";

    try {
        ControllerUsuario controllerUser = new ControllerUsuario();
        ControllerTicket controllerTicket = new ControllerTicket();

        Usuario user = controllerUser.get_user_by_id(idUsuario);
        if (user != null) {
            // Obtener los tickets del usuario
            List<Ticket> tickets = controllerTicket.get_tickets_by_usuario(idUsuario);

            JsonObject responseJson = new JsonObject();
            responseJson.add("usuario", new Gson().toJsonTree(user));
            responseJson.add("tickets", new Gson().toJsonTree(tickets));
            out = new Gson().toJson(responseJson);
        } else {
            out = "{\"response\":\"Usuario no encontrado\"}\n";
        }
    } catch (Exception e) {
        e.printStackTrace();
        out = String.format("{\"response\":\"Error al obtener usuario: %s\"}", e.getMessage());
    }

    return Response.ok(out).build();
}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get_all_users")
    public Response getAllUsers() {
        String out = "";
        
        try {
            ControllerUsuario controllerUser = new ControllerUsuario();
            List<Usuario> users = controllerUser.get_all_users();
            out = new Gson().toJson(users);
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error al obtener usuarios: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete_user")
    public Response deleteUserById(@QueryParam("idUsuario") int idUsuario) {
        String out = "";
        
        try {
            ControllerUsuario controllerUser = new ControllerUsuario();
            boolean deleted = controllerUser.delete_user(idUsuario);
            if (deleted) {
                out = "{\"response\":\"Usuario eliminado correctamente\"}\n";
            } else {
                out = "{\"response\":\"Usuario no encontrado\"}\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error al eliminar usuario: %s\"}", e.getMessage());
        }
        
        return Response.ok(out).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response login(@QueryParam("correo") String correo,
                          @QueryParam("contraseña") String contraseña) {
        String out = "";
        try {
            if (correo == null || correo.isEmpty() || contraseña == null || contraseña.isEmpty()) {
                out = "{\"error\" : \"Correo y contraseña son requeridos\"}";
                return Response.status(Response.Status.BAD_REQUEST).entity(out).build();
            }

            System.out.println("Correo recibido: " + correo);
            System.out.println("Contraseña recibida: " + contraseña);

            ControllerUsuario cu = new ControllerUsuario();
            ControllerTicket ct = new ControllerTicket();
            List<Usuario> usuarios = cu.get_all_users();

            for (Usuario usuario : usuarios) {
                System.out.println("Comparando con usuario: " + usuario.getCorreo());
                if (usuario.getCorreo().equals(correo) && usuario.getContraseña().equals(contraseña)) {
                    List<Ticket> tickets = ct.get_tickets_by_usuario(usuario.getIdUsuario());
                    JsonObject responseJson = new JsonObject();
                    responseJson.add("usuario", new Gson().toJsonTree(usuario));
                    responseJson.add("tickets", new Gson().toJsonTree(tickets));
                    out = new Gson().toJson(responseJson);
                    return Response.ok(out).build();
                }
            }

            out = "{\"error\" : \"Credenciales inválidas\"}";
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"error\" : \"%s\"}", e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(out).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get_tickets_by_alumno")
    public Response getTicketsByAlumno(@QueryParam("idAlumno") int idAlumno) {
        String out = "";

        try {
            ControllerTicket controllerTicket = new ControllerTicket();
            List<Ticket> tickets = controllerTicket.get_tickets_by_usuario(idAlumno);
            out = new Gson().toJson(tickets);
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error al obtener tickets: %s\"}", e.getMessage());
        }

        return Response.ok(out).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get_tickets_by_tecnico")
    public Response getTicketsByTecnico(@QueryParam("idTecnico") int idTecnico) {
        String out = "";

        try {
            ControllerTicket controllerTicket = new ControllerTicket();
            List<Ticket> tickets = controllerTicket.get_tickets_by_tecnico(idTecnico);
            out = new Gson().toJson(tickets);
        } catch (Exception e) {
            e.printStackTrace();
            out = String.format("{\"response\":\"Error al obtener tickets: %s\"}", e.getMessage());
        }

        return Response.ok(out).build();
    }
}
