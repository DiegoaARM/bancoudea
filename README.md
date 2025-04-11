# Banco UDEA - Backend

Este es el backend de una aplicación bancaria desarrollado con **Java Spring Boot**. Expone una API REST para la gestión de usuarios, cuentas y transacciones, incluyendo creación, edición, eliminación y transferencias entre cuentas.

## ▶️ Instrucciones para correr el proyecto

### 1. Configura la base de datos
En primera instancia descargar ```Mysql``` y crear un schematic llamado bancoudea, despues en el archivo ```application.properties```, definir los parámetros de conexión:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/bancoudea
spring.datasource.username=tu_usuario
spring.datasource.password=tu_clave
spring.jpa.hibernate.ddl-auto=update
```

Cambiar ```tu_usuario``` y ```tu_clave``` con las correspondientes a tu mysql.

### 2. Ejecutar el proyecto
Ejecutar el proyecto desde tu IDE preferido o con Maven:

```bash
mvn spring-boot:run
```

## 🛠️ Tecnologías utilizadas
* Java 17+

* Spring Boot

* Spring Web

* Spring Data JPA

* Spring Security

* MySQL

* Maven

* Lombok

* Mapstruct

* Swagger

## 📁 Estructura del proyecto
```
src/main/java/com/udea/bancoudea/
├── controller/        # Controladores REST
├── service/           # Lógica de negocio
├── repository/        # Acceso a datos con JPA
├── entity/            # Entidades (Usuario, Cuenta, Transacción)
├── mapper/            # Mappeos entre DTOs y entidades
├── dto/               # Objetos usados para transferir datos entre capas            
└── BancoUdeaApplication.java
```

## 🔐 Funcionalidades
* Registro y creación de clientes

* Creación automática de número de cuenta único

* Consulta y edición de clientes

* Eliminación de clientes

* Realización de transferencias

* Listado de transacciones por número de cuenta

* Seguridad básica con autenticación (JWT o básica según implementación)

## 📡 Comunicación con frontend
Este backend se conecta con un frontend hecho en React + Material UI. Las operaciones se consumen a través de endpoints REST expuestos en rutas como:

```
POST    /api/customers
GET     /api/customers
GET     /api/customers/{id}
PUT     /api/customers
DELETE  /api/customers
GET     /api/transactions
POST    /api/transactions
GET     /api/transactions/{accountNumber}
```
