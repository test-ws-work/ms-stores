CREATE TABLE stores (
    id SERIAL PRIMARY KEY,
    name VARCHAR(155) NOT NULL,
    address VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    complement VARCHAR(100),
    neighbor VARCHAR(100),
    state VARCHAR(4),
    city VARCHAR(60),
    country VARCHAR(4),
    user_id INT NOT NULL
);

CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    costumer_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    product_price DOUBLE PRECISION NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    tax INT NOT NULL,
    sale_date DATE,
    store_id INT NOT NULL,

    FOREIGN KEY (store_id) REFERENCES stores(id)
);