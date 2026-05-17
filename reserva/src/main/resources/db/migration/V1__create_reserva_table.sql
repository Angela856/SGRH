CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_habitacion BIGINT NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado VARCHAR(20) DEFAULT 'PENDIENTE'
);