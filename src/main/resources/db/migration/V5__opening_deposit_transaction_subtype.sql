-- R9-B: subtipo trazable para el fondeo inicial de una cuenta.
-- No modifica estructura; incorpora un dato de catálogo versionado e idempotente.

INSERT INTO SUBTIPO_TRANSACCION (
    CODIGO,
    NOMBRE,
    TIPO_MOVIMIENTO_BASE,
    DESCRIPCION,
    ESTADO
)
VALUES (
    'DEP_APERTURA_CUENTA',
    'Depósito inicial de apertura',
    'CREDITO',
    'Fondeo inicial aplicado durante la apertura de una cuenta',
    'ACTIVO'
)
ON DUPLICATE KEY UPDATE
    NOMBRE = VALUES(NOMBRE),
    TIPO_MOVIMIENTO_BASE = VALUES(TIPO_MOVIMIENTO_BASE),
    DESCRIPCION = VALUES(DESCRIPCION),
    ESTADO = 'ACTIVO';
