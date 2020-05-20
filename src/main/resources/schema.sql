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
    created_at datetime  NOT NULL,
    users_id int FOREIGN KEY REFERENCES users(id),
    customer_id int FOREIGN KEY REFERENCES customers(id)
)

CREATE TABLE comments
(
    type VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    created_at datetime  NOT NULL,
    orders_id int FOREIGN KEY REFERENCES orders(id)
)

CREATE TABLE clothes
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
)

CREATE TABLE items
(
    id int IDENTITY(1,1) PRIMARY KEY,
    clothes_id int FOREIGN KEY REFERENCES clothes(id)
)

CREATE TABLE orders_items
(
    orders_id int FOREIGN KEY REFERENCES orders(id),
    items_id int FOREIGN KEY REFERENCES items(id)
)

-- Insert information
INSERT INTO roles (name) VALUES ('Owner');
INSERT INTO roles (name) VALUES ('Assistant');
INSERT INTO roles (name) VALUES ('Worker');

INSERT INTO types (name) VALUES ('Cleaning Central');
INSERT INTO types (name) VALUES ('Delivery Point');

INSERT INTO addresses (street, number, city, zip) VALUES ('Rengøringsgade', '1', 'Sønderborg', '6400');

INSERT INTO locations (name, type_id, address_id) VALUES ('EcoSolutions', '1', '1');

INSERT INTO users (name, email, password, role_id, location_id) VALUES ('Jesper', 'o@eco.dk', '123', '1', '1');
INSERT INTO users (name, email, password, role_id, location_id) VALUES ('Assis', 'a@eco.dk', '123', '2', '1');