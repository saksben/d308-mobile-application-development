package com.wgu.pa.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.pa.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {

    //inserts data into the db
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    //updates data in the db
    @Update
    void update(Excursion excursion);

    //deletes data in the db
    @Delete
    void delete(Excursion excursion);

    //gets all excursions from the db
    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    //gets all excursions from the db that are associated with the given vacation
    @Query("SELECT * FROM EXCURSIONS WHERE vacationID=:vacation ORDER BY excursionID ASC")
    List<Excursion> getAssociatedExcursions(int vacation);
}
