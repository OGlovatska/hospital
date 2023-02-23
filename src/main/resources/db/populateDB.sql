DELETE FROM users;
DELETE FROM staff;
DELETE FROM patient;
DELETE FROM hospitalisation;
DELETE FROM appointment;
DELETE FROM staff_patient;

INSERT INTO users (first_name, last_name, email, password, role)
VALUES ('Viola', 'Robson', 'admin@gmail.com', '{noop}password', 'ADMIN'),
       ('Barbara', 'Henry', 'patient@gmail.com', '{noop}password', 'PATIENT'),
       ('Peter', 'Smith', 'doctor@gmail.com', '{noop}password', 'DOCTOR'),
       ('Julia', 'Stone', 'nurse@gmail.com', '{noop}password', 'NURSE');

INSERT INTO staff (user_id, specialisation)
VALUES (3, 'Surgeon'),
       (4, 'Chief nurse');

INSERT INTO patient(user_id, date_of_birth, gender)
VALUES (2, '1990-01-30', 'female');

INSERT INTO hospitalisation(patient_id, start_date, end_date, status)
VALUES (1, '2022-12-20', null, 'ON_TREATMENT');

INSERT INTO appointment(hospitalisation_id, patient_id, staff_id, date_time, type, description, conclusion, status)
VALUES (1, 1, 2, '2022-12-24 8:00:00', 'Analysis', 'General blood analysis', '', 'ASSIGNED'),
       (1, 1, 1, '2022-12-25 10:00:00', 'Surgery', 'Appendix surgery', '', 'ASSIGNED');

INSERT INTO staff_patient(staff_id, patient_id)
VALUES (1, 1),
       (2, 1);
