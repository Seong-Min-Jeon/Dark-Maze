package com.example.mobileprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

public class InGame extends AppCompatActivity {

    Button up, down, left, right;
    ImageView player, goal;

    int width;
    int height;
    int distY;
    int distX;

    private Chronometer chronometer;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        chronometer = (Chronometer) findViewById(R.id.time);

        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);

        player = (ImageView) findViewById(R.id.player);
        goal = (ImageView) findViewById(R.id.goal);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        distX = width/12;
        distY = height/20;

        setGoal(goal);

        if(!running) {
            chronometer.start();
            running = true;
        }

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setY((int)(player.getY() - distY));
                moveEvent(player, "-y");
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setY((int)(player.getY() + distY));
                moveEvent(player, "+y");
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setX(player.getX() - distX);
                moveEvent(player, "-x");
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setX(player.getX() + distX);
                moveEvent(player, "+x");
            }
        });

    }

    public void moveEvent(ImageView player, String rotate) {
        System.out.println(player.getX());
        System.out.println(player.getY());
        if(player.getY() > distY*10 || player.getY() < 0 || player.getX() > distX*10 || player.getX() < 0) {
            undo(player, rotate);
            return;
        }
        wall(player, rotate, player.getX(), player.getY());
        finish(player, goal);
    }

    public void wall(ImageView player, String rotate, float x, float y) {
        if(x == distX && y == distY) {
            undo(player, rotate);
            return;
        }

    }

    public void undo(ImageView player, String rotate) {
        if(rotate.equals("-y")) {
            player.setY((int)(player.getY() + distY));
        } else if(rotate.equals("+y")) {
            player.setY((int)(player.getY() - distY));
        } else if(rotate.equals("-x")) {
            player.setX(player.getX() + distX);
        } else if(rotate.equals("+x")) {
            player.setX(player.getX() - distX);
        } else {
            Toast.makeText(getApplicationContext(),
                    "알 수 없는 오류", Toast.LENGTH_SHORT).show();
        }
    }

    public void setGoal(ImageView goal) {
        goal.setX(distX*10);
        goal.setY(distY*10);
    }

    public void finish(ImageView player, ImageView goal) {
        if(player.getX() == goal.getX() && player.getY() == player.getY()) {
            chronometer.stop();
            running=false;
        }
    }

}
