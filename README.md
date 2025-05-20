# 🛠️ Cliente Sync – PostgreSQL ➝ Kafka ➝ MongoDB + API REST (Quarkus)

Este proyecto sincroniza datos desde PostgreSQL hacia MongoDB utilizando Apache Kafka y Debezium. Además, expone una API REST desarrollada con **Quarkus** para consultar clientes desde MongoDB.

---

## 🧱 Tecnologías usadas

- PostgreSQL 14
- Apache Kafka + Zookeeper (Confluent 7.3)
- Kafka Connect (Debezium 2.3)
- MongoDB 6
- Docker Compose
- Quarkus 3.10.0

---

## ⚙️ 1. Clonar el proyecto


git clone https://github.com/tu-usuario/cliente-sync.git 
cd cliente-sync


## 🐳 2. Levantar los servicios con Docker Compose
docker-compose up -d
- Servicios levantados:
- zookeeper
- kafka
- kafka-connect
- postgres
- mongodb

## 🗄️ 3. Configurar replicación MongoDB
Ingresar al contenedor de MongoDB
Ejecutar
- docker exec -it cliente-sync-mongodb-1 mongosh
Luego en consola ejecutar
- rs.initiate()

# 🧩 4. Crear tabla clientes en PostgreSQL
docker exec -it test-postgres-1 psql -U postgres -d clientes
Luego en consola ejecutar 
``` CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```
Insersion de datos de prueba

INSERT INTO public.clientes (nombre, apellido, email)
VALUES 
  ('Laura', 'Ramírez', 'laura.ramirez@example.com'),
  ('Carlos', 'Soto', 'carlos.soto@example.com'),
  ('Ana', 'González', 'ana.gonzalez@example.com');

# 🔌 5. Crear el connector Debezium PostgreSQL
``` 
$body = @'
{
    "name": "clientes-connector",
    "config": {
        "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
        "database.hostname": "postgres",
        "database.port": "5432",
        "database.user": "postgres",
        "database.password": "postgres",
        "database.dbname": "clientes",
        "database.server.name": "dbserver1",
        "table.include.list": "public.clientes",
        "plugin.name": "pgoutput",
        "database.history.kafka.bootstrap.servers": "kafka:29092",
        "database.history.kafka.topic": "schema-changes.clientes"
    }
}
'@


Invoke-WebRequest -Uri "TU_URL_DEL_CONNECT_API" -Method Post -Body $body -ContentType "application/json" 
```

## 🧪 6. Verificar mensajes en Kafka
``` 
docker exec -it cliente-sync-kafka-1 bash
 Blautech\Cliente-sync> 
 docker exec -it cliente-sync-kafka-connect-1 bash
 docker exec -it test-kafka-1 bash
--1  kafka-console-producer.sh --broker-list kafka-connect-1:9092 --topic clientes
--2 kafka-console-producer.sh --broker-list cliente-sync-kafka-1:9092 --topic clientes


 kafka-console-producer.sh --broker-list localhost:9092 --topic clientes


kafka-console-consumer \
  --bootstrap-server kafka:29092 \
  --topic dbserver1.public.clientes \
  --from-beginning \
  --max-messages 10
```
## 🔁 7. Crear Sink Connector para MongoDB


``` 
$body = @'
{
    "name": "clientes-mongodb-sink",
    "config": {
        "connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
        "tasks.max": "1",
        "topics": "dbserver1.public.clientes",
        "connection.uri": "mongodb://mongodb:27017",
        "database": "clientesdb",
        "collection": "clientes",
        "document.id.strategy": "com.mongodb.kafka.connect.sink.processor.id.strategy.ProvidedInKeyStrategy",
        "key.converter": "org.apache.kafka.connect.storage.StringConverter",
        "value.converter": "org.apache.kafka.connect.json.JsonConverter",
        "value.converter.schemas.enable": "false"
    }
}
'@

Invoke-WebRequest -Uri "TU_URL_DEL_CONNECT_API" -Method Post -Body $body -ContentType "application/json"

```

## 🔎 8. Verificar datos en MongoDB
ejecutar el siguiente comando en la consola de MongoDB

docker exec -it cliente-sync-mongodb-1 mongosh

seguido de: 

use clientesdb
db.clientes.find().pretty()

## 🌐 9. API REST en Quarkus
Ejecucion de la aplicación de Quarkus
./mvnw quarkus:dev

Descarga de dependencias 
ruta Blautech\cliente-pipeline>

./mvnw dependency:tree
luego de tener el BUILD SUCCES 
## ▶️ 10. Ejecutar Quarkus
EJECUTAMOS EN COSOLA :
Blautech\Blautech\cliente-pipeline> ./mvnw quarkus:dev
./mvnw quarkus:dev  

Una ves generado el procesos y se lenvante el ambiente procedermos validar [e], [r], [o], [:], [h]) te permiten interactuar con el modo de desarrollo de Quarkus:

[r]: Resume la ejecución de las pruebas (si tienes pruebas configuradas).
[:]: Abre una terminal dentro del proceso de Quarkus (útil para inspeccionar el entorno).
[h]: Muestra más opciones.

## 11 Valkidar consumo 
 http://localhost:8080/clientes

## escucha de topics
ejecutar en cosola  
kafka-console-producer --broker-list localhost:9092 --topic clientes
