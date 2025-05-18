DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    date NOT NULL,
    time    time NOT NULL,
    PRIMARY KEY (id)
);
