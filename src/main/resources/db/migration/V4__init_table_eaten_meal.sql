DROP TABLE IF EXISTS eaten_meal;

CREATE TABLE eaten_meal (
    id INT PRIMARY KEY AUTO_INCREMENT,
    portion DOUBLE NOT NULL,
    date_eaten DATE NOT NULL,
    meal_id INT,
    user_id INT,
    FOREIGN KEY (meal_id) REFERENCES meal(id)
);