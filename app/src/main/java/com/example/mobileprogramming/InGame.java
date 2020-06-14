package com.example.mobileprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class InGame extends AppCompatActivity {

    Button up, down, left, right;
    ImageView player;

    int width;
    int height;
    int distY;
    int distX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);

        player = (ImageView) findViewById(R.id.player);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        distX = width/6;
        distY = height/10;

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
        if(player.getY() > distY*5 || player.getY() < 0 || player.getX() > distX*5 || player.getX() < 0) {
            undo(player, rotate);
            return;
        }
        wall(player, rotate, player.getX(), player.getY());
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
}
