CREATE EVENT update_pet_age
ON SCHEDULE EVERY 1 DAY
STARTS '2022-11-19 00:30:00'
DO
UPDATE cocobob_dev.pet SET age = age + 1;