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

INSERT INTO departments (name, description)
VALUES
    ('Ressources humaines', 'Recrutement et accompagnement des collaborateurs'),
    ('Informatique', 'Developpement et support technique'),
    ('Finance', 'Comptabilite et gestion financiere')
ON CONFLICT (name) DO NOTHING;

INSERT INTO employees (employee_number, first_name, last_name, job_title, contract_type, department_id)
SELECT seed.employee_number, seed.first_name, seed.last_name, seed.job_title, seed.contract_type, d.id
FROM (VALUES
    ('WG-001', 'Sara', 'Amrani', 'Chargee RH', 'CDI', 'Ressources humaines'),
    ('WG-002', 'Youssef', 'Alaoui', 'Developpeur Java', 'CDI', 'Informatique'),
    ('WG-003', 'Meryem', 'Bennani', 'Comptable', 'CDD', 'Finance'),
    ('WG-004', 'Adam', 'Idrissi', 'Stagiaire developpement', 'STAGE', 'Informatique')
) AS seed(employee_number, first_name, last_name, job_title, contract_type, department_name)
JOIN departments d ON d.name = seed.department_name
ON CONFLICT (employee_number) DO NOTHING;
