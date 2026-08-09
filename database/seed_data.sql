-- Seed Data for Expense Tracker
USE expense_tracker;

-- Insert Standard Categories
INSERT INTO categories (id, name, icon_class, color_hex, type) VALUES
(1, 'Food', 'fa-utensils', '#FF6B6B', 'EXPENSE'),
(2, 'Travel', 'fa-plane', '#00CFCF', 'EXPENSE'),
(3, 'Shopping', 'fa-bag-shopping', '#B57EDC', 'EXPENSE'),
(4, 'Bills', 'fa-file-invoice-dollar', '#F39C12', 'EXPENSE'),
(5, 'Entertainment', 'fa-film', '#9B59B6', 'EXPENSE'),
(6, 'Education', 'fa-graduation-cap', '#3498DB', 'EXPENSE'),
(7, 'Health', 'fa-notes-medical', '#2ECC71', 'EXPENSE'),
(8, 'Others', 'fa-asterisk', '#95A5A6', 'EXPENSE');

-- Insert Demo User (Password: "Password@123" hashed with SHA-256)
INSERT INTO users (id, full_name, email, password_hash, phone_number, preferred_language, preferred_currency, monthly_income, occupation) VALUES
(1, 'Alex Morgan', 'alex@expensetracker.com', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', '+1 555-0199', 'en', 'INR', 85000.00, 'Senior Software Engineer');

-- Insert User Settings
INSERT INTO user_settings (user_id, theme, email_notifications, recurring_reminders, budget_alerts) VALUES
(1, 'light', TRUE, TRUE, TRUE);

-- Insert Sample Budgets for Current Month (August 2026)
INSERT INTO budgets (user_id, category_id, amount, period_type, month, year) VALUES
(1, NULL, 50000.00, 'MONTHLY', 8, 2026), -- Total overall monthly budget
(1, 1, 12000.00, 'MONTHLY', 8, 2026),     -- Food budget
(1, 2, 8000.00, 'MONTHLY', 8, 2026),      -- Travel budget
(1, 3, 10000.00, 'MONTHLY', 8, 2026),     -- Shopping budget
(1, 4, 15000.00, 'MONTHLY', 8, 2026);     -- Bills budget

-- Insert Sample Financial Goals
INSERT INTO goals (user_id, title, target_amount, saved_amount, target_date, category, icon_class, status) VALUES
(1, 'MacBook Pro M3', 180000.00, 125000.00, '2026-11-30', 'Gadget', 'fa-laptop', 'IN_PROGRESS'),
(1, 'Europe Vacation', 250000.00, 90000.00, '2027-05-15', 'Travel', 'fa-plane-departure', 'IN_PROGRESS'),
(1, 'Emergency Fund', 300000.00, 240000.00, '2026-12-31', 'Savings', 'fa-shield-halved', 'IN_PROGRESS');

-- Insert Sample Recurring Expenses
INSERT INTO recurring_expenses (user_id, name, category_id, amount, frequency, next_due_date, is_active, payment_mode) VALUES
(1, 'Apartment Rent', 4, 22000.00, 'MONTHLY', '2026-09-01', TRUE, 'Net Banking'),
(1, 'Netflix & Spotify Premium', 5, 999.00, 'MONTHLY', '2026-08-15', TRUE, 'Credit Card'),
(1, 'Fiber Internet Bill', 4, 1199.00, 'MONTHLY', '2026-08-18', TRUE, 'UPI'),
(1, 'Car Loan EMI', 4, 12500.00, 'MONTHLY', '2026-08-25', TRUE, 'Net Banking');

-- Insert Sample Expenses for Demo (Recent 3 Months)
INSERT INTO expenses (user_id, expense_name, category_id, label, amount, currency, converted_amount, payment_mode, expense_date, month, year, description) VALUES
(1, 'Weekly Grocery & Vegetables', 1, 'Essential', 3450.00, 'INR', 3450.00, 'UPI', '2026-08-01', 8, 2026, 'Supermarket shopping at Nature Basket'),
(1, 'Electricity Bill - July', 4, 'Utility', 2850.00, 'INR', 2850.00, 'UPI', '2026-08-02', 8, 2026, 'State Power Board payment'),
(1, 'Weekend Dinner with Friends', 1, 'Dining', 2100.00, 'INR', 2100.00, 'Credit Card', '2026-08-03', 8, 2026, 'Italian restaurant dinner'),
(1, 'Fuel & Petrol refill', 2, 'Commute', 2500.00, 'INR', 2500.00, 'Debit Card', '2026-08-04', 8, 2026, 'Full tank Shell station'),
(1, 'New Office Shoes & Shirt', 3, 'Apparel', 4800.00, 'INR', 4800.00, 'Credit Card', '2026-08-05', 8, 2026, 'Zara monsoon sale'),
(1, 'Doctor Consultation & Meds', 7, 'Healthcare', 1650.00, 'INR', 1650.00, 'UPI', '2026-08-06', 8, 2026, 'Annual wellness checkup'),
(1, 'Movie IMAX Tickets & Snacks', 5, 'Outing', 1200.00, 'INR', 1200.00, 'UPI', '2026-08-06', 8, 2026, 'Sci-Fi premiere IMAX'),
-- July Expenses
(1, 'House Rent July', 4, 'Rent', 22000.00, 'INR', 22000.00, 'Net Banking', '2026-07-01', 7, 2026, 'Monthly rent payment'),
(1, 'July Supermarket Grocery', 1, 'Essential', 8900.00, 'INR', 8900.00, 'UPI', '2026-07-10', 7, 2026, 'Bulk monthly kitchen stocking'),
(1, 'Flight Tickets for Weekend Getaway', 2, 'Travel', 11500.00, 'INR', 11500.00, 'Credit Card', '2026-07-15', 7, 2026, 'Goa weekend return flight'),
(1, 'Coursera Online Certification', 6, 'Skills', 3999.00, 'INR', 3999.00, 'Credit Card', '2026-07-20', 7, 2026, 'Cloud Architecture Certification'),
-- June Expenses
(1, 'House Rent June', 4, 'Rent', 22000.00, 'INR', 22000.00, 'Net Banking', '2026-06-01', 6, 2026, 'Monthly rent payment'),
(1, 'June Grocery & Supplies', 1, 'Essential', 7600.00, 'INR', 7600.00, 'UPI', '2026-06-12', 6, 2026, 'Supermarket supplies'),
(1, 'Car General Service & Oil Change', 2, 'Maintenance', 6800.00, 'INR', 6800.00, 'Debit Card', '2026-06-18', 6, 2026, 'Authorized Hyundai Service Center');

-- Insert Initial Achievements
INSERT INTO achievements (user_id, badge_key, badge_name, description, icon_class) VALUES
(1, 'SAVER_STAR', 'Savings Master', 'Saved over 20% of monthly income for 3 months', 'fa-piggy-bank'),
(1, 'STREAK_30', '30-Day Tracker', 'Logged expenses consistently for 30 consecutive days', 'fa-fire'),
(1, 'BUDGET_GUARDIAN', 'Budget Guardian', 'Stayed under total monthly budget for 2 months', 'fa-shield-heart');

-- Insert Notifications
INSERT INTO notifications (user_id, title, message, type) VALUES
(1, 'Budget Threshold Alert', 'You have reached 78% of your Food monthly budget (₹9,350 / ₹12,000)', 'WARNING'),
(1, 'Goal Milestone Achieved', 'You passed 65% on your MacBook Pro M3 goal!', 'SUCCESS'),
(1, 'Upcoming Recurring Payment', 'Apartment Rent (₹22,000) is due in 5 days.', 'INFO');
