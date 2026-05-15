CREATE TABLE pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_reserva BIGINT NOT NULL,
    monto DOUBLE NOT NULL,
    metodo VARCHAR(50),
    fecha_pago DATETIME
);