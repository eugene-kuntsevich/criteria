INSERT INTO cities (name) VALUES ('Gomel');
INSERT INTO cities (name) VALUES ('Minsk');
INSERT INTO cities (name) VALUES ('Grodno');

INSERT INTO languages (name_lng) VALUES ('EN');
INSERT INTO languages (name_lng) VALUES ('DE');

INSERT INTO localizations (lang_id, city_id, value) VALUES (1,2,'Minsk_EN');
INSERT INTO localizations (lang_id, city_id, value) VALUES (2,2,'Minsk_DE');
INSERT INTO localizations (lang_id, city_id, value) VALUES (1,3,'Grodno_EN');
INSERT INTO localizations (lang_id, city_id, value) VALUES (2,3,'Grodno_DE');