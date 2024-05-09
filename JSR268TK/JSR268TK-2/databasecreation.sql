-- Create the main database for the health care system
CREATE DATABASE health_care_system;

-- Set the context to the newly created database
USE health_care_system;

-- Admins table storing sensitive encrypted data
CREATE TABLE admins (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,      -- Primary key for admin identity
    national_id BIGINT UNIQUE,                       -- Unique national ID for each admin
    first_name VARCHAR(50),                        -- Encrypted first name
    last_name VARCHAR(50),                         -- Encrypted last name
    picture VARCHAR(64),                           -- Encrypted image data
    email VARCHAR(336),                            -- Encrypted email address
    phone_number VARCHAR(32),                      -- Encrypted phone number for backup
    address VARCHAR(256),                          -- Encrypted home address
    hashed_codepin VARCHAR(72),                      -- BCrypt hashed security code
    card_expiring_date DATE,                         -- Expiry date of admin's identification card
    user_public_key VARCHAR(256),                  -- Public key for secure communication
    session_key VARCHAR(128),                      -- Session key for secure transactions
    INDEX idx_admins (admin_id)                      -- Index on admin_id for faster lookups
);

-- Doctors table storing non-sensitive and sensitive data
CREATE TABLE doctors (
    doctor_id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- Primary key for doctor identity
    first_name VARCHAR(32),                          -- Doctor's first name
    last_name VARCHAR(32),                           -- Doctor's last name
    gender ENUM('female', 'male'),                   -- Gender of the doctor
    picture VARCHAR(64),                             -- Profile picture of the doctor
    national_id BIGINT UNIQUE NOT NULL,              -- Unique national ID for each doctor
    about TEXT,                                      -- Detailed information about the doctor
    email VARCHAR(255),                              -- Doctor's email address
    phone_number VARCHAR(16),                        -- Doctor's phone number
    address VARCHAR(256),                            -- Doctor's home address
    hashed_codepin VARCHAR(72) NOT NULL,             -- BCrypt hashed password
    card_expiring_date DATE NOT NULL,                -- Expiry date of doctor's identification card
    user_public_key VARCHAR(256),                  -- Public key for secure communication
    doctor_status ENUM('ACTIVE', 'INACTIVE', 'DISABLED'), -- Status of the doctor
    INDEX idx_doctors (doctor_id, national_id)       -- Composite index for faster lookups
);

-- Patients table storing encrypted sensitive data
CREATE TABLE patients (
    patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,    -- Primary key for patient identity
    first_name VARCHAR(64),                        -- Encrypted first name of the patient
    last_name VARCHAR(50),                         -- Encrypted last name of the patient
    date_of_birth DATE,                              -- Date of birth of the patient
    national_id BIGINT UNIQUE,                       -- Unique national ID for each patient
    gender ENUM('female', 'male'),                   -- Gender of the patient
    email VARCHAR(400),                            -- Encrypted email address of the patient
    phone_number VARCHAR(50),                      -- Encrypted phone number for backup
    session_key VARCHAR(200),                      -- Session key for secure transactions
    address VARCHAR(300),                          -- Encrypted address of the patient
    INDEX idx_patients (patient_id, national_id)     -- Composite index for faster lookups
);

-- Treatments table linking patients and doctors
CREATE TABLE treatments (
    treatment_id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Primary key for each treatment record
    patient_id BIGINT,                               -- Foreign key to patients table
    doctor_id BIGINT,                                -- Foreign key to doctors table
    treatment_date DATE,                             -- Date of the treatment
    treatment_type VARCHAR(100),                     -- Type of treatment administered
    description TEXT,                                -- Detailed description of the treatment
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id), -- Link to patients table
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)      -- Link to doctors table
);

-- Appointments table for scheduling and tracking
CREATE TABLE appointments (
    appointment_id BIGINT AUTO_INCREMENT PRIMARY KEY,-- Primary key for each appointment
    patient_id BIGINT,                               -- Foreign key to patients table
    doctor_id BIGINT,                                -- Foreign key to doctors table
    treatment_id BIGINT,                             -- Foreign key to treatments table
    appointment_date_time DATETIME,                  -- Date and time of the appointment
    duration INT,                                    -- Duration of the appointment in minutes
    notes TEXT,                                      -- Notes about the appointment
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id), -- Link to patients table
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),    -- Link to doctors table
    FOREIGN KEY (treatment_id) REFERENCES treatments(treatment_id) -- Link to treatments table
);

-- Access history to patients table for audit and tracking
CREATE TABLE accesses_to_patient (
    access_id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- Primary key for each access record
    patient_id BIGINT,                               -- Foreign key to patients table
    doctor_id BIGINT,                                -- Foreign key to doctors table
    access_date DATETIME,                            -- Date and time of access
    access_type ENUM('QUICK CHECK', 'NEW SESSION', 'UPDATE'), -- Type of access
    access_duration INT,                             -- Duration of the access in minutes
    INDEX idx_access_tp (access_id, patient_id, doctor_id), -- Composite index for faster lookups
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id), -- Link to patients table
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)    -- Link to doctors table
);

-- Access history to doctors table for audit and tracking
CREATE TABLE accesses_to_doctor (
    access_id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- Primary key for each access record
    doctor_id BIGINT,                                -- Foreign key to doctors table
    access_date DATETIME,                            -- Date and time of access
    access_duration INT,                             -- Duration of the access in minutes
    INDEX idx_access_tp (access_id, doctor_id),      -- Index for faster lookups
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)    -- Link to doctors table
);
