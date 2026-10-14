CREATE TABLE IF NOT EXISTS departments (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS employees (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    employee_number VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    job_title VARCHAR(100) NOT NULL,
    contract_type VARCHAR(10) NOT NULL CHECK (contract_type IN ('CDI', 'CDD', 'STAGE')),
    department_id BIGINT NOT NULL,
    CONSTRAINT fk_employees_department FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_employees_department_id ON employees(department_id);

-- INSERT INTO departments (name, description)
-- VALUES ('Human Resources', 'Recruitment, employee administration, and HR policies'), ('Engineering', 'Software development and technical operations')
--     ON CONFLICT (name) DO NOTHING;
--
-- INSERT INTO employees (employee_number, first_name, last_name, job_title, contract_type, department_id)
-- SELECT 'EMP-001', 'Sara', 'Amrani', 'HR Assistant', 'CDI', d.id
-- FROM departments d WHERE d.name = 'Human Resources'
--     ON CONFLICT (employee_number) DO NOTHING;