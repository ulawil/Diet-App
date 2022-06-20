ALTER TABLE meal_food RENAME ingredients;
ALTER TABLE ingredients ADD COLUMN grams INT NOT NULL;