-- Expense Tracker - Database Schema
-- MySQL 8.0 / Tomcat 10 Compatible DDL

CREATE DATABASE IF NOT EXISTS expense_tracker;
USE expense_tracker;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS receipts;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS recurring_expenses;
DROP TABLE IF EXISTS goals;
DROP TABLE IF EXISTS budgets;
DROP TABLE IF EXISTS expenses;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS user_settings;
DROP TABLE IF EXISTS users;

-- 1. Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    avatar_url VARCHAR(255) DEFAULT 'assets/images/default-avatar.png',
    preferred_language VARCHAR(10) DEFAULT 'en',
    preferred_currency VARCHAR(10) DEFAULT 'INR',
    monthly_income DECIMAL(12,2) DEFAULT 0.00,
    occupation VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Categories Table
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    icon_class VARCHAR(50) DEFAULT 'fa-tags',
    color_hex VARCHAR(20) DEFAULT '#B57EDC',
    type ENUM('EXPENSE', 'INCOME') DEFAULT 'EXPENSE'
);

-- 3. Expenses Table
CREATE TABLE expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    expense_name VARCHAR(150) NOT NULL,
    category_id INT NOT NULL,
    label VARCHAR(50) DEFAULT 'General',
    amount DECIMAL(12,2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'INR',
    converted_amount DECIMAL(12,2) NOT NULL, -- normalized to base currency
    payment_mode ENUM('Cash', 'Credit Card', 'Debit Card', 'UPI', 'Net Banking', 'Others') DEFAULT 'UPI',
    expense_date DATE NOT NULL,
    month INT NOT NULL,
    year INT NOT NULL,
    description TEXT,
    receipt_path VARCHAR(255),
    is_recurring BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
    INDEX idx_user_date (user_id, expense_date),
    INDEX idx_category (category_id)
);

-- 4. Budgets Table
CREATE TABLE budgets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT, -- NULL for total overall budget
    amount DECIMAL(12,2) NOT NULL,
    period_type ENUM('MONTHLY', 'YEARLY') DEFAULT 'MONTHLY',
    month INT,
    year INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_budget (user_id, category_id, period_type, month, year)
);

-- 5. Financial Goals Table
CREATE TABLE goals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    target_amount DECIMAL(12,2) NOT NULL,
    saved_amount DECIMAL(12,2) DEFAULT 0.00,
    target_date DATE NOT NULL,
    category VARCHAR(50) DEFAULT 'General',
    icon_class VARCHAR(50) DEFAULT 'fa-bullseye',
    status ENUM('IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'IN_PROGRESS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 6. Recurring Expenses Table
CREATE TABLE recurring_expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(150) NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    frequency ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY') DEFAULT 'MONTHLY',
    next_due_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    payment_mode VARCHAR(50) DEFAULT 'UPI',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- 7. Notifications Table
CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    type ENUM('SUCCESS', 'WARNING', 'DANGER', 'INFO') DEFAULT 'INFO',
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 8. Achievements / Gamification Table
CREATE TABLE achievements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    badge_key VARCHAR(50) NOT NULL,
    badge_name VARCHAR(100) NOT NULL,
    description TEXT,
    icon_class VARCHAR(50) DEFAULT 'fa-award',
    unlocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_badge (user_id, badge_key)
);

-- 9. User Settings Table
CREATE TABLE user_settings (
    user_id INT PRIMARY KEY,
    theme VARCHAR(20) DEFAULT 'light',
    email_notifications BOOLEAN DEFAULT TRUE,
    recurring_reminders BOOLEAN DEFAULT TRUE,
    budget_alerts BOOLEAN DEFAULT TRUE,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
