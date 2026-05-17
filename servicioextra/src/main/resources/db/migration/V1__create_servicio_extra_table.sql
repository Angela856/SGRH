CREATE TABLE servicio_extra (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_reserva BIGINT NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL
);