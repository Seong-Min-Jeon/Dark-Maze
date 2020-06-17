package com.example.mobileprogramming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class InGame extends AppCompatActivity {

    Button up, down, left, right;
    ImageView player, goal;

    int width;
    int height;
    int distY;
    int distX;

    Random rnd = new Random();

    private ArrayList<String> list = new ArrayList<>();

    private Chronometer chronometer;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        //action bar 삭제
        getSupportActionBar().hide();

        //xml연동
        chronometer = (Chronometer) findViewById(R.id.time);

        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);

        player = (ImageView) findViewById(R.id.player);
        goal = (ImageView) findViewById(R.id.goal);

        //디바이스에 따른 조정
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        distX = width/24;
        distY = height/40;

        //맵 랜덤 생성
        map();

        //목표지점 설정
        setGoal(goal);

        //타이머 시스템
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }

        //조작 버튼 클릭 리스너
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
        if(player.getY() > distY*20 || player.getY() < 0 || player.getX() > distX*20 || player.getX() < 0) {
            undo(player, rotate);
            return;
        }
        wall(player, rotate, player.getX(), player.getY());
        finish(player, goal);
    }

    public void wall(ImageView player, String rotate, float x, float y) {
        int xNum;
        int yNum;
        if(distX == 0) {
            xNum = 0;
        } else {
            xNum = (int)(x/distX);
        }
        if(distY == 0) {
            yNum = 0;
        } else {
            yNum = (int)(y/distY);
        }

        String coor = "";
        if(xNum < 10) {
            coor += "0" + xNum;
        } else {
            coor += xNum;
        }
        if(yNum < 10) {
            coor += "0" + yNum;
        } else {
            coor += yNum;
        }

        if(list.contains(coor)) {
            undo(player, rotate);
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

    public void map() {
        try {
            String num1;
            String num2;
            for(int i = 0 ; i < 21 ; i++) {
                for(int j = 0 ; j < 21 ; j++) {
                    if(i < 10) {
                        num1 = ("0" + i);
                    } else {
                        num1 = Integer.toString(i);
                    }
                    if(j < 10) {
                        num2 = ("0" + j);
                    } else {
                        num2 = Integer.toString(j);
                    }
                    list.add(num1+num2);
                }
            }
            list.remove("0000");
            String current = "0000";
            ArrayList<String> rotate = new ArrayList<>();
            for(int i  = 0 ; i < 30 ; i++) {
                rotate.add("R");
            }
            for(int i  = 0 ; i < 30 ; i++) {
                rotate.add("D");
            }
            for(int i  = 0 ; i < 10 ; i++) {
                rotate.add("L");
            }
            for(int i  = 0 ; i < 10 ; i++) {
                rotate.add("U");
            }
            Collections.shuffle(rotate);
            while(rotate.size() != 0) {
                String direction = rotate.remove(rotate.size()-1);
                int[] loc = new int[4];
                char[] chars;
                chars = current.toCharArray();
                for(int i = 0 ; i < 4 ; i++) {
                    loc[i] = Character.getNumericValue(chars[i]);
                }
                switch (direction) {
                    case "R":
                        if(loc[0] == 2) {
                            int rndNum = rnd.nextInt(rotate.size()-2);
                            rotate.add(rotate.get(rndNum));
                            rotate.set(rndNum, direction);
                            continue;
                        }
                        if(loc[1] == 9) {
                            loc[0]++;
                            loc[1] = 0;
                        } else {
                            loc[1]++;
                        }
                        current = Integer.toString(loc[0]) + loc[1] + loc[2] + loc[3];
                        if(list.contains(current)) {
                            list.remove(current);
                        }
                        break;
                    case "D":
                        if(loc[2] == 2) {
                            int rndNum = rnd.nextInt(rotate.size()-2);
                            rotate.add(rotate.get(rndNum));
                            rotate.set(rndNum, direction);
                            continue;
                        }
                        if(loc[3] == 9) {
                            loc[2]++;
                            loc[3] = 0;
                        } else {
                            loc[3]++;
                        }
                        current = Integer.toString(loc[0]) + loc[1] + loc[2] + loc[3];
                        if(list.contains(current)) {
                            list.remove(current);
                        }
                        break;
                    case "L":
                        if(loc[0] == 0 && loc[1] == 0) {
                            int rndNum = rnd.nextInt(rotate.size()-2);
                            rotate.add(rotate.get(rndNum));
                            rotate.set(rndNum, direction);
                            continue;
                        }
                        if(loc[1] == 0) {
                            loc[0]--;
                            loc[1] = 9;
                        } else {
                            loc[1]--;
                        }
                        current = Integer.toString(loc[0]) + loc[1] + loc[2] + loc[3];
                        if(list.contains(current)) {
                            list.remove(current);
                        }
                        break;
                    case "U":
                        if(loc[2] == 0 && loc[3] == 0) {
                            int rndNum = rnd.nextInt(rotate.size()-2);
                            rotate.add(rotate.get(rndNum));
                            rotate.set(rndNum, direction);
                            continue;
                        }
                        if(loc[3] == 0) {
                            loc[2]--;
                            loc[3] = 9;
                        } else {
                            loc[3]--;
                        }
                        current = Integer.toString(loc[0]) + loc[1] + loc[2] + loc[3];
                        if(list.contains(current)) {
                            list.remove(current);
                        }
                        break;
                }
            }
            int size = list.size();
            for(int _ = 0 ; _ < 80 ; _++) {
                size = list.size();
                list.remove(rnd.nextInt(size-2));
            }
        } catch(Exception e) {
            map();
        }
    }

    public void setGoal(ImageView goal) {
        goal.setX(distX*20);
        goal.setY(distY*20);
    }

    public void finish(ImageView player, ImageView goal) {
        if(player.getX() == goal.getX() && player.getY() == goal.getY()) {
            chronometer.stop();
            running=false;
            int clearTime = (int) (SystemClock.elapsedRealtime()-chronometer.getBase());
            System.out.println(String.valueOf(clearTime) + "time");

            AlertDialog.Builder dlg = new AlertDialog.Builder(InGame.this);
            dlg.setTitle("Finish!");
            dlg.setMessage("Game Time: " + ((int)(clearTime / 100)/10.0) + " seconds");
            dlg.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    restart();
                }
            });
            dlg.show();
        }
    }

    public void restart(){
        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
