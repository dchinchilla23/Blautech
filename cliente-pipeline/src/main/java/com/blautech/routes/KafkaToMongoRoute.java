
package com.blautech.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;

// import javax.enterprise.context.ApplicationScoped; se genera cambio por que no se usa
// se genera add por que se usa en la version 17

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaToMongoRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:clientes?brokers=localhost:9092")
            .routeId("KafkaToMongoRoute")
            .log("Mensaje recibido desde Kafka: ${body}")
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .to("mongodb:clienteDB?database=clienteDB&collection=clientes&operation=insert");
    }
}
