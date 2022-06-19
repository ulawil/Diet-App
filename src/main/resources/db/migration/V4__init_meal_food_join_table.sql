DROP TABLE IF EXISTS meal_food;

CREATE TABLE meal_food (
    meal_id INT NOT NULL,
    food_id INT NOT NULL,
    PRIMARY KEY(meal_id, food_id),
    FOREIGN KEY(meal_id) REFERENCES meals(id),
    FOREIGN KEY(food_id) REFERENCES foods(id)
)
