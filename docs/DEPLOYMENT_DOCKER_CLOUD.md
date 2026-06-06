# Despliegue Docker/Cloud - core-admin-service

## Objetivo

Este microservicio queda preparado para ejecutarse en local, Docker Compose y nube sin valores hardcodeados.

## Variables principales

| Variable | Uso |
|---|---|
| `SPRING_PROFILES_ACTIVE` | Perfil activo. Para Docker usar `docker`. |
| `SERVER_PORT` | Puerto HTTP del microservicio. Por defecto `8083`. |
| `ADMIN_DB_URL` | JDBC URL de la base `banquito_core_admin_db`. |
| `ADMIN_DB_USER` | Usuario de MySQL. |
| `ADMIN_DB_PASSWORD` | Clave de MySQL. |
| `JWT_ISSUER` | Emisor esperado del token JWT generado por `identity-access-service`. |
| `JWT_SECRET` | Secreto compartido para validar JWT. Debe ser el mismo en todos los microservicios. |
| `DEMO_DATA_ENABLED` | Habilita datos demo si existe inicializador. |
| `FLYWAY_ENABLED` | Controla migraciones Flyway. |

## Ejecución local

```powershell
mvn clean package
mvn spring-boot:run
```

## Ejecución Docker

```powershell
docker build -t banquito/core-admin-service:local .
docker run --rm -p 8083:8083 --env-file .env.example banquito/core-admin-service:local
```

## Nube / Docker Compose

En nube, `ADMIN_DB_URL` debe apuntar al nombre del servicio MySQL dentro de la red Docker:

```env
ADMIN_DB_URL=jdbc:mysql://mysql-admin:3306/banquito_core_admin_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Guayaquil
```

## Kong

Este servicio será expuesto por Kong mediante REST/OpenAPI:

```text
/api/v1/admin/**
```

La comunicación interna entre microservicios del Core deberá implementarse por gRPC en la fase de integración, por ejemplo:

```text
core-account-service -> core-admin-service
```

para validar sucursales, subtipos, parámetros, ventanas operativas, feriados y routing codes.
