CREATE DATABASE IF NOT EXISTS business_onboarding;

USE business_onboarding;

-- Applications table
CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id VARCHAR(255) NOT NULL UNIQUE,
    legal_name VARCHAR(255) NOT NULL,
    legal_structure_type VARCHAR(50) NOT NULL,
    country_of_incorporation VARCHAR(100) NOT NULL,
    business_registration_number VARCHAR(100) NOT NULL UNIQUE,
    tax_identification_number VARCHAR(100),
    industry_type VARCHAR(50) NOT NULL,
    primary_contact_person VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) NOT NULL UNIQUE,
    estimated_annual_turnover DECIMAL(19,2),
    expected_monthly_transaction_volume DECIMAL(19,2),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    processed_at TIMESTAMP NULL,
    processing_notes TEXT,
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_application_id (application_id)
);

-- Directors table
CREATE TABLE directors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    national_id_passport VARCHAR(100) NOT NULL,
    country_of_residence VARCHAR(100) NOT NULL,
    FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE,
    INDEX idx_application_id (application_id)
);

-- Ultimate Beneficial Owners table
CREATE TABLE ultimate_beneficial_owners (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    national_id_passport VARCHAR(100) NOT NULL,
    country_of_residence VARCHAR(100) NOT NULL,
    ownership_percentage DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE,
    INDEX idx_application_id (application_id)
);

-- Documents table
CREATE TABLE documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE,
    INDEX idx_application_id (application_id)
);
