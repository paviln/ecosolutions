-- Create Database Structure
USE MASTER
GO
DROP DATABASE IF EXISTS OMS

CREATE DATABASE OMS;
GO
use OMS;

CREATE TABLE roles
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name varchar(50) NOT NULL
)

CREATE TABLE addresses
(
    id int IDENTITY(1,1) PRIMARY KEY,
    street varchar(20) NOT NULL,
    number varchar(20) NOT NULL,
    city varchar(20) NOT NULL,
    zip varchar(4) NOT NULL,
)

CREATE TABLE types
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name varchar(20) NOT NULL
)

CREATE TABLE locations
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name varchar(50) NOT NULL,
    priority int NOT NULL,
    type_id int FOREIGN KEY REFERENCES types(id),
    address_id int FOREIGN KEY REFERENCES addresses(id)
)

CREATE TABLE users
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    role_id int FOREIGN KEY REFERENCES roles(id),
    location_id int FOREIGN KEY REFERENCES locations(id)
)

CREATE TABLE customers
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL
)

CREATE TABLE orders
(
    id int IDENTITY(1,1) PRIMARY KEY,
    status int NOT NULL,
    location_id int NOT NULL,
    created_at datetime  NOT NULL,
    user_id int FOREIGN KEY REFERENCES users(id) ON DELETE CASCADE,
    customer_id int FOREIGN KEY REFERENCES customers(id) ON DELETE CASCADE
)

CREATE TABLE clothes
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
)

CREATE TABLE items
(
    id int IDENTITY(1,1) PRIMARY KEY,
    quantity int NOT NULL,
    order_id int FOREIGN KEY REFERENCES orders(id) ON DELETE CASCADE,
    clothe_id int FOREIGN KEY REFERENCES clothes(id) ON DELETE CASCADE
)

-- Insert information
INSERT INTO roles (name) VALUES ('Owner');
INSERT INTO roles (name) VALUES ('Assistant');
INSERT INTO roles (name) VALUES ('Driver');
INSERT INTO roles (name) VALUES ('Worker');


INSERT INTO types (name) VALUES ('Cleaning Central');
INSERT INTO types (name) VALUES ('Delivery Point');

INSERT INTO addresses (street, number, city, zip) VALUES ('Rengøringsgade', '1', 'Sønderborg', '6400');
INSERT INTO addresses (street, number, city, zip) VALUES ('Borgade', '23', 'Sønderborg', '6400');


INSERT INTO locations (name, priority, type_id, address_id) VALUES ('EcoSolutions', '1', '1', '1');
INSERT INTO locations (name, priority, type_id, address_id) VALUES ('Borgen', '2', '2', '2');


INSERT INTO users (name, email, password, role_id, location_id) VALUES ('Jesper', 'o@eco.dk', '123', '1', '1');
INSERT INTO users (name, email, password, role_id, location_id) VALUES ('Assis', 'a@eco.dk', '123', '2', '2');
INSERT INTO users (name, email, password, role_id, location_id) VALUES ('Driver', 'd@eco.dk', '123', '3', '1');
INSERT INTO users (name, email, password, role_id, location_id) VALUES ('Worker', 'w@eco.dk', '123', '4', '1');


INSERT INTO clothes (name) VALUES ('Habit');
INSERT INTO clothes (name) VALUES ('Coat');
INSERT INTO clothes (name) VALUES ('Dress');
INSERT INTO clothes (name) VALUES ('Suit');
INSERT INTO clothes (name) VALUES ('Cooton coat');
INSERT INTO clothes (name) VALUES ('Windbreaker');
INSERT INTO clothes (name) VALUES ('Blouse');
INSERT INTO clothes (name) VALUES ('Skirt');
INSERT INTO clothes (name) VALUES ('Jacket');
INSERT INTO clothes (name) VALUES ('Trousers');
INSERT INTO clothes (name) VALUES ('Shirt');
INSERT INTO clothes (name) VALUES ('Blankets');
INSERT INTO clothes (name) VALUES ('Curtains');