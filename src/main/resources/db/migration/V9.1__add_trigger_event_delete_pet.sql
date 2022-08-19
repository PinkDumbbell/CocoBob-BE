create trigger pet before delete on pet for each row
    begin
        update pet set deleted = true where pet_id = OLD.pet_id;
    end;