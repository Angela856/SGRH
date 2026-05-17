CREATE TABLE tarifas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    habitacion_id BIGINT NOT NULL,
    precio_noche DOUBLE NOT NULL,
    temporada VARCHAR(255) NOT NULL
);