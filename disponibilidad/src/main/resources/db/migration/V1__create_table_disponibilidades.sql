CREATE TABLE disponibilidad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    habitacion_id BIGINT NOT NULL,
    disponible BOOLEAN NOT NULL
);