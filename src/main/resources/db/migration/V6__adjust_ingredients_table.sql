ALTER TABLE ingredients MODIFY meal_id INT;
ALTER TABLE ingredients MODIFY food_id INT;
ALTER TABLE ingredients DROP FOREIGN KEY ingredients_ibfk_1;
ALTER TABLE ingredients DROP FOREIGN KEY ingredients_ibfk_2;
ALTER TABLE ingredients DROP PRIMARY KEY;
ALTER TABLE ingredients ADD COLUMN id INT PRIMARY KEY AUTO_INCREMENT;