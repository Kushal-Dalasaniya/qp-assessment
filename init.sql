CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL
);

-- Table for Grocery Items
CREATE TABLE grocery_items (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

-- Table for User Orders
CREATE TABLE user_orders (
    order_id SERIAL PRIMARY KEY,
	quantity INT NOT NULL,
	item_id INT,
    user_id INT, -- Add user_id column to associate orders with users
    order_timestamp TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (item_id) REFERENCES grocery_items(item_id)
);