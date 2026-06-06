# CHANGELOG - Audit Fix

## Corrección aplicada

- Se restauró el endpoint `GET /api/v1/admin/business-calendar/{date}/next-business-day`.
- Se agregó el método de servicio `obtenerSiguienteDiaHabil(LocalDate fecha)` para calcular el siguiente día hábil posterior a la fecha recibida, respetando fines de semana y feriados activos.
- No se eliminaron endpoints ni reglas existentes.

## Justificación

El endpoint es necesario para reglas operativas de cut-off, procesamiento de pagos masivos, diferimiento a siguiente día hábil y validaciones del Core/Switch mediante REST/OpenAPI a través de Kong cuando corresponda.
