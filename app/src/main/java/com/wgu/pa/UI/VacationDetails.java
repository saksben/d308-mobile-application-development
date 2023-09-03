package com.wgu.pa.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.wgu.pa.R;
import com.wgu.pa.database.Repository;
import com.wgu.pa.entities.Excursion;
import com.wgu.pa.entities.Vacation;

public class VacationDetails extends AppCompatActivity {

    String title;
    int vacationID;

    EditText editTitle;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());

        editTitle = findViewById(R.id.titletext);
        vacationID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        editTitle.setText(title);
    }

    //creates the menu and fills it with the items
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //takes user back to VacationList
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        //if the user selects the Save Vacation menu option...
        if (item.getItemId() == R.id.vacationsave) {
            System.out.println("Mexico save was selected");
            Vacation vacation;
            //if the vacation does not already exist...
            if (vacationID == -1) {
                //if the vacation list is empty, make this vacation its first vacation
                if (repository.getmAllVacations().size() == 0) vacationID = 1;
                //else make this vacation the last in the list
                else vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                vacation = new Vacation(vacationID, editTitle.getText().toString());
                repository.insert(vacation);
                this.finish();
                System.out.println("Mexico -1: " + vacationID);
            } else {
                //else if the vacation already exists, update it with the user supplied details
                vacation = new Vacation(vacationID, editTitle.getText().toString());
                repository.update(vacation);
                this.finish();
                System.out.println("Mexico Not -1: " + vacationID);
            }
            System.out.println("MenuItem didn't work");
        }

        //if the user selects the menu option Delete Vacation...
        if (item.getItemId() == R.id.vacationdelete) {
            for (Vacation vac : repository.getmAllVacations()) {
                if (vac.getVacationId() == vacationID) currentVacation = vac;
            }
            numExcursions = 0;
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }
            //if the vacation has any associated excursions, prevent deletion of the vacation, otherwise delete it
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
}