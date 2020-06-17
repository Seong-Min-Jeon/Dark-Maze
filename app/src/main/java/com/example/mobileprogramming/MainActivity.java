package com.example.mobileprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton start, exit, about;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //action bar color
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#000000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("");

        //버튼
        start = (ImageButton) findViewById(R.id.start);
        exit = (ImageButton) findViewById(R.id.exit);
        about = (ImageButton) findViewById(R.id.about);

        //player music
        stopPlayer();
        player = MediaPlayer.create(this, R.raw.music1);
        player.start();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                Intent intent = new Intent(MainActivity.this, InGame.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.music1:
                stopPlayer();
                player = MediaPlayer.create(this, R.raw.music1);
                player.start();
                return true;
            case R.id.music2:
                stopPlayer();
                player = MediaPlayer.create(this, R.raw.music2);
                player.start();
                return true;
            case R.id.music3:
                stopPlayer();
                player = MediaPlayer.create(this, R.raw.music3);
                player.start();
                return true;
            case R.id.stop:
                stopPlayer();
                return true;
        }
        return false;
    }

    public void stopPlayer() {
        if(player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

}
