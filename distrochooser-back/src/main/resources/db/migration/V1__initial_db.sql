BEGIN;
CREATE TABLE IF NOT EXISTS feedback (
    id SERIAL PRIMARY KEY,
    result_link VARCHAR(300) NOT NULL,
    selected_distro VARCHAR(20)
);
COMMIT;
