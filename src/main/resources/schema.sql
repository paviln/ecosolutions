USE MASTER
GO
DROP DATABASE IF EXISTS OMS

CREATE DATABASE OMS;
GO
use OMS;

CREATE TABLE roles
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name varchar(20) NOT NULL
)

CREATE TABLE zips
(
    code int IDENTITY(1,1) PRIMARY KEY,
    city varchar(20) NOT NULL
)

CREATE TABLE addresses
(
    id int IDENTITY(1,1) PRIMARY KEY,
    street varchar(20) NOT NULL,
    number varchar(20) NOT NULL,
    zips_id int FOREIGN KEY REFERENCES zips(code)
)

CREATE TABLE types
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name varchar(20) NOT NULL
)

CREATE TABLE locations
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name varchar(20) NOT NULL,
    address varchar(255) NOT NULL,
    types_id int FOREIGN KEY REFERENCES types(id),
    addresses_id int FOREIGN KEY REFERENCES addresses(id)
)

CREATE TABLE users
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    role_id int FOREIGN KEY REFERENCES roles(id),
    locations_id int FOREIGN KEY REFERENCES locations(id)
)

CREATE TABLE customers
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    phone VARCHAR(20) NOT NULL
)

CREATE TABLE orders
(
    id int IDENTITY(1,1) PRIMARY KEY,
    status int NOT NULL,
    created_at timestamp NOT NULL,
    users_id int FOREIGN KEY REFERENCES users(id),
    customer_id int FOREIGN KEY REFERENCES customers(id)
)

CREATE TABLE comments
(
    type VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    created_at timestamp NOT NULL,
    orders_id int FOREIGN KEY REFERENCES orders(id)
)

CREATE TABLE clothes
(
    id int IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(20) NOT NULL
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