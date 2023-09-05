package com.wgu.pa.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wgu.pa.R;
import com.wgu.pa.database.Repository;
import com.wgu.pa.entities.Excursion;
import com.wgu.pa.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    String title;
    String hotel;
    int vacationID;

    EditText editTitle;
    EditText editHotel;
    TextView editStartDate;
    TextView editEndDate;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    List<Excursion> filteredExcursions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());

        editTitle = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        vacationID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        hotel = getIntent().getStringExtra("hotel");
        editTitle.setText(title);
        editHotel.setText(hotel);

        //makes button to add Excursions
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
            }
        });

        //finds the excursions associated with the vacation and populates the RecyclerView list with it
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (Excursion e: repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setmExcursions(filteredExcursions);

        //sets up date info
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate = findViewById(R.id.startDate);
        editEndDate = findViewById(R.id.endDate);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //sets the calendar instance to whatever is selected
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //sets the calendar instance to whatever is selected
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    //updates the date displayed after it has been chosen
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
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
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String startDateString = sdf.format(myCalendarStart.getTime());
            String endDateString = sdf.format(myCalendarEnd.getTime());

            try {
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);
                if (endDate.before(startDate)) {
                    Toast.makeText(this, "End date must be AFTER start date", Toast.LENGTH_LONG).show();
                } else {
                    Vacation vacation;
                    //if the vacation does not already exist...
                    if (vacationID == -1) {
                        //if the vacation list is empty, make this vacation its first vacation
                        if (repository.getmAllVacations().size() == 0) vacationID = 1;
                        //else make this vacation the last in the list
                        else vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateString, endDateString);
                        repository.insert(vacation);
                        this.finish();
                    } else {
                        //else if the vacation already exists, update it with the user supplied details
                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateString, endDateString);
                        repository.update(vacation);
                        this.finish();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
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

        //sets alert for Start Date
        if (item.getItemId() == R.id.alertstart) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title + " is starting";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //sets alert for End Date
        if (item.getItemId() == R.id.alertend) {
            String dateFromScreen = editEndDate.getText().toString();
            String alert = "Vacation " + title + " is ending";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //sets alert for both Start Date and End Date
        if (item.getItemId() == R.id.alertfull) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title + " is starting";
            alertPicker(dateFromScreen, alert);
            dateFromScreen = editEndDate.getText().toString();
            alert = "Vacation " + title + " is ending";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //allows the Vacation info to be shared
        //prepares contents of the note input to share
        if (item.getItemId() == R.id.share) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Vacation Shared");
            //need to put the items into a constructed String or else each item will replace the last
            StringBuilder shareData = new StringBuilder();
            shareData.append("Vacation title: " + editTitle.getText().toString() + "\n");
            shareData.append("Hotel name: " + editHotel.getText().toString() + "\n");
            shareData.append("Start Date: " + editStartDate.getText().toString() + "\n");
            shareData.append("End Date: " + editEndDate.getText().toString() + "\n");
            for (int i = 0; i < filteredExcursions.size(); i++) {
                shareData.append("Excursion " + (i + 1) + ": " + filteredExcursions.get(i).getExcursionTitle() + "\n");
            }
            sentIntent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }

        return true;
    }

    //the logic to set the date alarm, inclusive of start/end/both
    public void alertPicker(String dateFromScreen, String alert) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
        intent.putExtra("key", alert);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //finds the excursions associated with the vacation and populates the RecyclerView list with it
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e: repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setmExcursions(filteredExcursions);
    }
}