CREATE TABLE comentarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    habitacion_id BIGINT NOT NULL,
    huesped_id BIGINT NOT NULL,
    texto VARCHAR(255) NOT NULL,
    calificacion INT NOT NULL
);