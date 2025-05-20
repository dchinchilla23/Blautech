package com.blautech.routes;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/kafka-connectors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class KafkaConnectResource {

    @POST
    @Path("/create")
    public Response createConnector(ConnectorConfig config) {
        // Aquí iría la lógica para procesar la configuración del conector
        System.out.println("Recibida configuración del conector: " + config);
        return Response.status(Response.Status.CREATED).entity(config).build();
    }
}