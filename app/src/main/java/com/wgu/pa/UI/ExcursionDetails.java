package com.wgu.pa.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wgu.pa.R;
import com.wgu.pa.database.Repository;
import com.wgu.pa.entities.Excursion;
import com.wgu.pa.entities.Vacation;

import java.util.ArrayList;

public class ExcursionDetails extends AppCompatActivity {
    String title;
    int excursionID;
    int vacationID;
    EditText editTitle;
    Repository repository;
    Excursion currentExcursion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.excursionTitle);
        editTitle.setText(title);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
    }

    //makes a menu and populates it with menu items
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    //adds menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        //navigation for user to go to Vacation Details page
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        //if the user selects the Save Excursion menu option...
        if (item.getItemId() == R.id.excursionsave) {
            Excursion excursion;
            //if the excursion does not already exist...
            if (excursionID == -1) {
                //if the excursion list is empty, make this excursion its first excursion
                if (repository.getmAllExcursions().size() == 0) excursionID = 1;
                //else make this excursion the last in the list
                else excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID);
                repository.insert(excursion);
                this.finish();
            } else {
                //else if the excursion already exists, update it with the user supplied details
                excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID);
                repository.update(excursion);
                this.finish();
            }
            return true;
        }

        //if the user selects the menu option Delete Excursion...
        if (item.getItemId() == R.id.excursiondelete) {
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) currentExcursion = excursion;
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}