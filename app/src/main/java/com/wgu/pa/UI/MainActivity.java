package com.wgu.pa.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wgu.pa.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

//    public static Random rand = new Random();
//    public static int numAlert = rand.nextInt(99999);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make button to go to VacationList
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VacationList.class);
                intent.putExtra("test", "Information sent");
                startActivity(intent);
            }
        });
    }
}