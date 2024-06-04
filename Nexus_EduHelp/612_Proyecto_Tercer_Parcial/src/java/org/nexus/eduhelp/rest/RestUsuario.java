package org.nexus.eduhelp.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
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
import org.nexus.eduhelp.controller.ControllerUsuario;
import org.nexus.eduhelp.model.Usuario;

@Path("usuario")
public class RestUsuario {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("register_user")
    public Response save(@FormParam("nombre") String nombre, 
                         @FormParam("apellido") String apellido,
                         @FormParam("correo") String correo,
                         @FormParam("tipo") String tipo,
                         @FormParam("contraseña") String contraseña,
                         @FormParam("matricula")int matricula){
        
        String out = "";
        
        try {
            Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
            Timestamp fechaActualizacion = fechaCreacion;
            
            Usuario user = new Usuario(0, nombre, apellido, correo, tipo, contraseña, matricula, fechaCreacion, fechaActualizacion);
            ControllerUsuario controllerUser = new ControllerUsuario();
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
    public Response update(@FormParam("idUsuario") int idUsuario, 
                           @FormParam("nombre") String nombre, 
                           @FormParam("apellido") String apellido,
                           @FormParam("correo") String correo,
                           @FormParam("tipo") String tipo,
                           @FormParam("contraseña") String contraseña,
                           @FormParam("matricula") int matricula) {
        
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
            Usuario user = controllerUser.get_user_by_id(idUsuario);
            if (user != null) {
                out = new Gson().toJson(user);
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
        List<Usuario> usuarios = cu.get_all_users();

        for (Usuario usuario : usuarios) {
            System.out.println("Comparando con usuario: " + usuario.getCorreo());
            if (usuario.getCorreo().equals(correo) && usuario.getContraseña().equals(contraseña)) {
                out = String.format("{\"idUsuario\" : \"%d\"}", usuario.getIdUsuario());
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
}
