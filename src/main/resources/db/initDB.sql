DROP database IF EXISTS hospital;
CREATE database hospital;
USE hospital;

CREATE TABLE users
(
    id         INT PRIMARY KEY auto_increment,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(100) UNIQUE,
    password   VARCHAR(100),
    role       VARCHAR(10)  NOT NULL
);

CREATE TABLE staff
(
    id             INT PRIMARY KEY auto_increment,
    user_id        INTEGER      NOT NULL UNIQUE,
    specialisation VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE patient
(
    id            INT PRIMARY KEY auto_increment,
    user_id       INTEGER     NOT NULL UNIQUE,
    date_of_birth DATE        NOT NULL,
    gender        VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE hospitalisation
(
    id         INT PRIMARY KEY auto_increment,
    patient_id INTEGER     NOT NULL,
    start_date TIMESTAMP   NOT NULL,
    end_date   TIMESTAMP   NULL,
    status     VARCHAR(50) NOT NULL,
    diagnosis  VARCHAR(2000),
    FOREIGN KEY (patient_id) REFERENCES patient (id)
);

CREATE TABLE appointment
(
    id                 INT PRIMARY KEY auto_increment,
    hospitalisation_id INTEGER       NOT NULL,
    patient_id         INTEGER       NOT NULL,
    staff_id           INTEGER       NOT NULL,
    date_time          TIMESTAMP     NOT NULL,
    type               VARCHAR(100)  NOT NULL,
    description        VARCHAR(2000) NOT NULL,
    conclusion         VARCHAR(2000),
    status             VARCHAR(50)   NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patient (id),
    FOREIGN KEY (staff_id) REFERENCES staff (id),
    FOREIGN KEY (hospitalisation_id) REFERENCES hospitalisation (id)
);

CREATE TABLE staff_patient
(
    id         INT PRIMARY KEY auto_increment,
    staff_id   INTEGER NOT NULL,
    patient_id INTEGER NOT NULL,
    CONSTRAINT staff_patient_idx UNIQUE (staff_id, patient_id),
    FOREIGN KEY (staff_id) REFERENCES staff (id),
    FOREIGN KEY (patient_id) REFERENCES patient (id)
);