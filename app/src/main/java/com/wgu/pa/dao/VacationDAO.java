package com.wgu.pa.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.pa.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {

    //inserts data into the db
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    //updates data in the db
    @Update
    void update(Vacation vacation);

    //deletes data from the db
    @Delete
    void delete(Vacation vacation);

    //gets all vacations from the db
    @Query("SELECT * FROM VACATIONS ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();
}
