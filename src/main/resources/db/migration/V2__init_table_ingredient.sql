DROP TABLE IF EXISTS ingredient;

CREATE TABLE ingredient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    grams DOUBLE NOT NULL,
    kcal DOUBLE NOT NULL,
    carbs DOUBLE NOT NULL,
    protein DOUBLE NOT NULL,
    fat DOUBLE NOT NULL,
    meal_id INT
);