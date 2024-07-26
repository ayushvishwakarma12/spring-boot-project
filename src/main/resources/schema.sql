CREATE TABLE IF NOT EXISTS managers (
    manager_id UUID PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    user_id UUID PRIMARY KEY,
    full_name VARCHAR(250) NOT NULL,
    mob_num VARCHAR(15) NOT NULL,
    pan_num VARCHAR(10) NOT NULL,
    manager_id UUID REFERENCES managers(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);