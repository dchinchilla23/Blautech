package org.diegochinchilla.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.diegochinchilla.models.Cliente;


import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @GET
    public List<Cliente> listAll() {
        return Cliente.listAll();
    }

    @GET
    @Path("/{id}")
    public Cliente getById(@PathParam("id") String id) {
        return Cliente.findById(new org.bson.types.ObjectId(id));
    }

    @POST
    public Response create(Cliente cliente) {
        cliente.persist();
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, Cliente cliente) {
        Cliente existingCliente = Cliente.findById(new org.bson.types.ObjectId(id));
        if (existingCliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingCliente.nombre = cliente.nombre;
        existingCliente.apellido = cliente.apellido;
        existingCliente.email = cliente.email;
        existingCliente.update();
        return Response.ok(existingCliente).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        boolean deleted = Cliente.deleteById(new org.bson.types.ObjectId(id));
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}