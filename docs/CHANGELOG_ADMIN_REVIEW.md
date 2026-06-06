# Changelog técnico - core-admin-service

## Versión cloud-ready

Cambios aplicados:

- Se parametrizó `application.yml` y `application-docker.yml` mediante variables de entorno.
- Se agregó `Dockerfile` preparado para Java 21 y ejecución con usuario no root.
- Se agregó `.env.example`, `.gitignore` y `.dockerignore`.
- Se agregó `@ConfigurationPropertiesScan` para registrar propiedades como `JwtProperties`.
- Se homologó el manejo de errores con `BusinessException` y `@RestControllerAdvice`.
- Se agregaron manejadores REST para errores de seguridad:
  - `RestAuthenticationEntryPoint` para 401.
  - `RestAccessDeniedHandler` para 403.
- Se agregaron respuestas estructuradas para:
  - JSON inválido.
  - parámetros faltantes.
  - parámetros con tipo incorrecto.
  - restricciones de integridad.
  - recursos inexistentes.
  - métodos HTTP no permitidos.
- Se preservó el contrato `admin_catalog_service.proto`.
- Se copió el SQL vigente a `docs/database/02_core_admin_db.sql`.

## Cobertura funcional revisada

El servicio cubre el bounded context administrativo:

- Sucursales.
- Feriados/calendario hábil.
- Siguiente día hábil.
- Parámetros del Core.
- Ventanas operativas/cut-off.
- Instituciones financieras y routing codes.
- Subtipos de cuenta.
- Subtipos de transacción.
- Usuarios operativos del Core.

No debe cubrir:

- Login, RBAC o scopes: pertenecen a `identity-access-service`.
- Tarifario de pagos masivos: pertenece al Switch.
- Saldos/transacciones: pertenecen a `core-account-service`.
- Plan contable/EOD: pertenece a `core-accounting-service`.
