-- Reset database to avoid conflicts
DROP DATABASE IF EXISTS smarthostel;
CREATE DATABASE IF NOT EXISTS smarthostel;
USE smarthostel;

-- 1. USERS TABLE
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'student') DEFAULT 'student',
    course VARCHAR(100) DEFAULT NULL,
    year_of_study VARCHAR(20) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. ROOMS TABLE
CREATE TABLE IF NOT EXISTS rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    capacity INT NOT NULL,
    occupied INT DEFAULT 0,
    status ENUM('available', 'full') DEFAULT 'available'
);

-- 3. ALLOCATIONS TABLE
CREATE TABLE IF NOT EXISTS allocations (
    allocation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    allocation_date DATE,
    status ENUM('pending', 'active', 'inactive', 'rejected') DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- 4. FEES TABLE
CREATE TABLE IF NOT EXISTS fees (
    fee_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_amount DECIMAL(10, 2),
    paid_amount DECIMAL(10, 2) DEFAULT 0.00,
    due_amount DECIMAL(10, 2),
    status ENUM('paid', 'partial', 'unpaid') DEFAULT 'unpaid',
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY uk_fees_user (user_id)
);

-- 5. COMPLAINTS TABLE
CREATE TABLE IF NOT EXISTS complaints (
    complaint_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category VARCHAR(50) NOT NULL DEFAULT 'other',
    description TEXT NOT NULL,
    status ENUM('pending', 'resolved') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 6. VISITORS TABLE
CREATE TABLE IF NOT EXISTS visitors (
    visitor_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    visitor_name VARCHAR(100) NOT NULL,
    visit_date DATE NOT NULL,
    purpose VARCHAR(255),
    status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================================
-- INSERT SAMPLE DATA
-- ============================================================

-- Admin user
INSERT INTO users (name, email, phone, password, role) VALUES
('Admin User', 'admin@hostel.com', '9800000001', 'admin123', 'admin');

-- Sample Students
INSERT INTO users (name, email, phone, password, course, year_of_study, role) VALUES
('Aarav Sharma', 'aarav@gmail.com', '9811111111', 'student123', 'BCA', '2', 'student'),
('Sita Rai', 'sita@gmail.com', '9822222222', 'student123', 'BIT', '1', 'student'),
('Rohan Thapa', 'rohan@gmail.com', '9833333333', 'student123', 'BBA', '3', 'student');

-- Sample Rooms
INSERT INTO rooms (room_number, capacity, occupied, status) VALUES
('101', 2, 1, 'available'),
('102', 2, 0, 'available'),
('103', 1, 0, 'available'),
('201', 3, 2, 'available'),
('202', 2, 0, 'available'),
('203', 1, 0, 'available');

-- Update room status (Using safe mode override)
SET SQL_SAFE_UPDATES = 0;
UPDATE rooms SET status = 'available' WHERE occupied < capacity;
UPDATE rooms SET status = 'full' WHERE occupied >= capacity;
SET SQL_SAFE_UPDATES = 1;

-- Sample Fees
INSERT INTO fees (user_id, total_amount, paid_amount, due_amount, status) VALUES
(2, 50000.00, 30000.00, 20000.00, 'partial'),
(3, 50000.00, 50000.00, 0.00, 'paid'),
(4, 50000.00, 0.00, 50000.00, 'unpaid');

-- Sample Complaints
INSERT INTO complaints (user_id, category, description, status) VALUES
(2, 'Electricity', 'Light not working in room 101', 'pending'),
(3, 'Water', 'No hot water supply', 'resolved');

-- Sample Visitors
INSERT INTO visitors (user_id, visitor_name, visit_date, purpose, status) VALUES
(2, 'Ramesh Sharma', '2025-04-20', 'Family Visit', 'approved'),
(3, 'Sunita Rai', '2025-04-21', 'Personal', 'pending');

SELECT 'Database setup complete!' AS Message;
