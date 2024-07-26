INSERT INTO managers (full_name, is_active)
VALUES
    ('Manager One', true),
    ('Manager Two', true),
    ('Manager Three', true);


INSERT INTO users (full_name, mob_num, pan_num, manager_id, created_at, updated_at, is_active)
VALUES
    ('John Doe', '9876543210', 'AABCP1234C', '11111111-1111-1111-1111-111111111111', '2024-07-25 10:00:00', NULL, true),
    ('Jane Smith', '8765432109', 'AABCD5678E', '22222222-2222-2222-2222-222222222222', '2024-07-25 10:30:00', NULL, true),
    ('Alice Johnson', '7654321098', 'AABEF7890F', NULL, '2024-07-25 11:00:00', NULL, true);
