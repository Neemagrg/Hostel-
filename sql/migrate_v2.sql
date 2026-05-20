-- Optional: upgrade an existing smarthostel database to match the current app.
-- Run statements one at a time; skip any that error with "Duplicate column" or "Duplicate key name".

USE smarthostel;

ALTER TABLE users ADD COLUMN course VARCHAR(100) DEFAULT NULL;
ALTER TABLE users ADD COLUMN year_of_study VARCHAR(20) DEFAULT NULL;

ALTER TABLE complaints ADD COLUMN category VARCHAR(50) NOT NULL DEFAULT 'other';

ALTER TABLE fees ADD UNIQUE KEY uk_fees_user (user_id);

ALTER TABLE allocations MODIFY COLUMN status ENUM('pending', 'active', 'inactive', 'rejected') DEFAULT 'pending';
