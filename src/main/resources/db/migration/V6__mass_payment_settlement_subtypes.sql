-- R9-D: subtipos financieros para devolución y reverso de fondos prefundeados.
INSERT INTO SUBTIPO_TRANSACCION
    (CODIGO, NOMBRE, TIPO_MOVIMIENTO_BASE, DESCRIPCION, ESTADO)
VALUES
    ('LIBERACION_PM', 'Liberación reserva pago masivo', 'CREDITO',
     'Devolución de fondos no consumidos de un lote de pagos masivos', 'ACTIVO'),
    ('REVERSO_RESERVA_PM', 'Reverso reserva pago masivo', 'CREDITO',
     'Compensación total de una reserva de pagos masivos sin consumos', 'ACTIVO')
ON DUPLICATE KEY UPDATE
    NOMBRE = VALUES(NOMBRE),
    TIPO_MOVIMIENTO_BASE = VALUES(TIPO_MOVIMIENTO_BASE),
    DESCRIPCION = VALUES(DESCRIPCION),
    ESTADO = 'ACTIVO';
