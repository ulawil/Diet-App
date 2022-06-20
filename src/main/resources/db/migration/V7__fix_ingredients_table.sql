ALTER TABLE ingredients ADD FOREIGN KEY(meal_id) REFERENCES meals(id);
ALTER TABLE ingredients ADD FOREIGN KEY(food_id) REFERENCES foods(id);