CREATE TABLE habitaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    numero VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    precio_base DOUBLE NOT NULL
);