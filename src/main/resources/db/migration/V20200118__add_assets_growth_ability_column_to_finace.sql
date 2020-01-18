ALTER TABLE finance ADD COLUMN assets_growth_ability varchar(50) default null;
ALTER TABLE finance ADD COLUMN total_ability varchar(50) default null;
ALTER TABLE finance MODIFY COLUMN user_id varchar(50) UNIQUE;