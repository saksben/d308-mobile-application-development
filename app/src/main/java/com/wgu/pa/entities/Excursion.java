package com.wgu.pa.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionTitle;
    private int vacationID;

    public Excursion(int excursionID, String excursionTitle, int vacationID) {
        this.excursionID = excursionID;
        this.excursionTitle = excursionTitle;
        this.vacationID = vacationID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }
}
