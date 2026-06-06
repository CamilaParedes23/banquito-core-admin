INSERT IGNORE INTO SUCURSAL (UUID_SUCURSAL, CODIGO_SUCURSAL, NOMBRE, CIUDAD, DIRECCION) VALUES
(UUID(), '001', 'Sucursal Norte', 'Quito', 'Quito Norte'),
(UUID(), '002', 'Sucursal Sur', 'Quito', 'Quito Sur'),
(UUID(), '003', 'Sucursal Centro', 'Quito', 'Quito Centro'),
(UUID(), '004', 'Sucursal Valles', 'Quito', 'Cumbaya / Tumbaco'),
(UUID(), '999', 'Sucursal Digital', 'Quito', 'Canal Digital');

INSERT IGNORE INTO PARAMETRO_CORE (CODIGO,NOMBRE,VALOR_TEXTO,TIPO_DATO,DESCRIPCION) VALUES
('IVA_PORCENTAJE','IVA vigente','15.00','DECIMAL','IVA vigente para servicios gravados'),
('BANQUITO_ROUTING_CODE','Routing Code BanQuito','BQTO001','STRING','Codigo de institucion financiera de BanQuito'),
('CORE_CURRENCY','Moneda funcional','USD','STRING','Moneda operativa del banco');

INSERT IGNORE INTO VENTANA_OPERATIVA (CODIGO,NOMBRE,DOMINIO_OPERATIVO,HORA_INICIO,HORA_CORTE,HORA_FIN,DIAS_APLICA,ACCION_DESPUES_CORTE) VALUES
('CORE_CONTABLE','Ventana contable Core','CONTABLE','00:00:00','20:00:00','23:59:59','LUN,MAR,MIE,JUE,VIE','SIGUIENTE_DIA_HABIL'),
('SWITCH_INTERBANK','Ventana interbancaria Switch','SWITCH','08:00:00','18:00:00','23:59:59','LUN,MAR,MIE,JUE,VIE','ENCOLAR'),
('SFTP_PAGOS_MASIVOS','Carga SFTP pagos masivos','SFTP','00:00:00','18:00:00','23:59:59','LUN,MAR,MIE,JUE,VIE','ENCOLAR'),
('BANCA_WEB_EMPRESAS','Banca web empresas','CANAL','00:00:00','18:00:00','23:59:59','LUN,MAR,MIE,JUE,VIE','ENCOLAR'),
('VENTANILLA','Ventanilla sucursal','CANAL','08:30:00','17:00:00','17:00:00','LUN,MAR,MIE,JUE,VIE','RECHAZAR');

INSERT IGNORE INTO INSTITUCION_FINANCIERA (ROUTING_CODE,NOMBRE,ES_BANQUITO) VALUES
('BQTO001','Banco BanQuito',1),
('PICH001','Banco Pichincha',0),
('PACF001','Banco del Pacifico',0),
('GYQL001','Banco Guayaquil',0);

INSERT IGNORE INTO SUBTIPO_CUENTA (CODIGO,TIPO_BASE,NOMBRE,DESCRIPCION) VALUES
('AHO_STD','AHORROS','Cuenta de Ahorros Estandar','Producto base de ahorro'),
('COR_STD','CORRIENTE','Cuenta Corriente Estandar','Producto base corriente'),
('COR_NOM','NOMINA','Cuenta Corriente Nomina','Cuenta empresarial para nomina');

INSERT IGNORE INTO SUBTIPO_TRANSACCION (CODIGO,NOMBRE,TIPO_MOVIMIENTO_BASE,DESCRIPCION) VALUES
('DEP_VENTANILLA','Deposito por Ventanilla','CREDITO','Deposito en efectivo'),
('RET_VENTANILLA','Retiro por Ventanilla','DEBITO','Retiro de efectivo'),
('TRF_P2P_DEB','Transferencia P2P Debito','DEBITO','Salida P2P'),
('TRF_P2P_CRE','Transferencia P2P Credito','CREDITO','Entrada P2P'),
('RESERVA_PM','Reserva Pago Masivo','DEBITO','Reserva/fondeo de lote de pagos masivos'),
('PAGO_ONUS','Pago masivo On-Us','CREDITO','Credito a beneficiario BanQuito'),
('CONSUMO_OFFUS','Consumo pago Off-Us','DEBITO','Consumo de reserva por pago interbancario'),
('COMISION_PM','Comision Pagos Masivos','DEBITO','Debito de comision calculada por Switch'),
('REVERSO','Reverso transaccional','CREDITO','Movimiento compensatorio');
