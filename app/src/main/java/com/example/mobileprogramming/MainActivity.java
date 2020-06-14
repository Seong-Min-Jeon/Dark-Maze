package com.example.mobileprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View View) {
                Intent intent = new Intent(MainActivity.this, InGame.class);
                startActivity(intent);
            }
        });

    }


}
