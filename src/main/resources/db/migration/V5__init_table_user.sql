DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    locked BOOLEAN,
    enabled BOOLEAN,
    daily_kcal_goal DOUBLE DEFAULT 0,
    daily_carbs_goal_pct DOUBLE DEFAULT 0,
    daily_protein_goal_pct DOUBLE DEFAULT 0,
    daily_fat_goal_pct DOUBLE DEFAULT 0
);

ALTER TABLE meal ADD FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE eaten_meal ADD FOREIGN KEY (user_id) REFERENCES user(id);