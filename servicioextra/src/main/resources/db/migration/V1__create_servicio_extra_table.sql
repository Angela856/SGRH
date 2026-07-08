CREATE TABLE servicios_extras (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_reserva BIGINT NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL
);