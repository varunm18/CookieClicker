package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    ImageButton wilson, football, playbook;
    TextView plus, totalText, passiveText, footballText, playbookText;
    int passive, footballAmt, footballPrice, playbookAmt, playbookPrice;
    ConstraintLayout constraintLayout;
    ImageView broncos, footballIcon, playbookIcon;
    public static AtomicInteger total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wilson = findViewById(R.id.imageButton);
        football = findViewById(R.id.imageButton2);
        playbook = findViewById(R.id.imageButton3);
        constraintLayout = findViewById(R.id.constraint);
        totalText = findViewById(R.id.textView);
        passiveText = findViewById(R.id.textView2);
        footballText = findViewById(R.id.textView3);
        playbookText = findViewById(R.id.textView4);
        broncos = findViewById(R.id.imageView);

        footballText.setText("Footballs: 0\nPrice: 10");
        playbookText.setText("Playbooks: 0\nPrice: 50");

        wilson.setImageResource(R.drawable.wilson);
        football.setImageResource(R.drawable.football);
        playbook.setImageResource(R.drawable.playbook);
        broncos.setImageResource(R.drawable.broncos);
        

        total = new AtomicInteger();
        passive = 0;
        footballAmt = 0;
        footballPrice = 10;
        playbookAmt = 0;
        playbookPrice = 50;

        ScaleAnimation scale = new ScaleAnimation(1, 1.3f, 1, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(200);

        RotateAnimation rotate = new RotateAnimation(0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(400);

        wilson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wilson.startAnimation(scale);
                new PlusThread().start();

                total.getAndAdd(1);
                totalText.setText(total.toString()+" Touchdowns");
            }
        });

        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total.get()>=footballPrice)
                {
                    football.startAnimation(rotate);
                    total.getAndAdd(-footballPrice);

                    footballAmt+=1;
                    passive+=1;
                    footballPrice *= 1.5;

                    passiveText.setText(passive+" cps");
                    footballText.setText("Footballs: "+footballAmt+"\nPrice: "+footballPrice);

                    new FootballThread().start();
                    new FootballIconThread().start();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not Enough Touchdowns", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total.get()>=playbookPrice)
                {
                    playbook.startAnimation(rotate);
                    total.getAndAdd(-playbookPrice);

                    playbookAmt+=1;
                    passive+=5;
                    playbookPrice *= 1.8;

                    passiveText.setText(passive+" cps");
                    playbookText.setText("Playbooks: "+playbookAmt+"\nPrice: "+playbookPrice);

                    new PlaybookThread().start();
                    new PlaybookIconThread().start();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not Enough Touchdowns", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class PlusThread extends Thread{
        public void run()
        {
            TranslateAnimation animation = new TranslateAnimation(0f, 0f, 0f, -700f);
            animation.setDuration(1000);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    plus = new TextView(MainActivity.this);
                    plus.setId(View.generateViewId());
                    plus.setText("+1");
                    ConstraintLayout.LayoutParams layout = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    plus.setLayoutParams(layout);

                    constraintLayout.addView(plus);

                    ConstraintSet set = new ConstraintSet();
                    set.clone(constraintLayout);

                    set.connect(plus.getId(), ConstraintSet.TOP, wilson.getId(), ConstraintSet.TOP);
                    set.connect(plus.getId(), ConstraintSet.BOTTOM, wilson.getId(), ConstraintSet.BOTTOM);
                    set.connect(plus.getId(), ConstraintSet.LEFT, wilson.getId(), ConstraintSet.LEFT);
                    set.connect(plus.getId(), ConstraintSet.RIGHT, wilson.getId(), ConstraintSet.RIGHT);
                    set.setVerticalBias(plus.getId(), (float)(Math.random()*0.45f+0.35f));
                    set.setHorizontalBias(plus.getId(), (float)(Math.random()*0.65f+0.15f));
                    set.applyTo(constraintLayout);

                    plus.startAnimation(animation);
                    plus.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    private class FootballIconThread extends  Thread{
        public void run()
        {
            TranslateAnimation animation = new TranslateAnimation(-710f, 0, 930f-(60*(footballAmt+playbookAmt)), 0);
            animation.setDuration(1000);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    footballIcon = new ImageView(MainActivity.this);
                    footballIcon.setId(View.generateViewId());
                    footballIcon.setImageResource(R.drawable.football);

                    ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(100, 100);
                    footballIcon.setLayoutParams(layout);

                    constraintLayout.addView(footballIcon);

                    ConstraintSet set = new ConstraintSet();
                    set.clone(constraintLayout);

                    set.connect(footballIcon.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                    set.connect(footballIcon.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                    set.connect(footballIcon.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                    set.connect(footballIcon.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
                    set.setVerticalBias(footballIcon.getId(), 0.14f+(0.05f*(float)(footballAmt+playbookAmt)));
                    set.setHorizontalBias(footballIcon.getId(), 0.978f);
                    set.applyTo(constraintLayout);

                    footballIcon.startAnimation(animation);
                }
            });
        }
    }

    private class FootballThread extends Thread{
        public void run(){
            new Timer().scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    total.getAndAdd(1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            totalText.setText(total.toString()+" Touchdowns");
                        }
                    });
                }
            }, 1000, 1000);
        }
    }

    private class PlaybookIconThread extends  Thread{
        public void run()
        {
            TranslateAnimation animation = new TranslateAnimation(-200f, 0, 930f-(75*(footballAmt+playbookAmt)), 0);
            animation.setDuration(1000);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    playbookIcon = new ImageView(MainActivity.this);
                    playbookIcon.setId(View.generateViewId());
                    playbookIcon.setImageResource(R.drawable.playbook);

                    ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(60, 60);
                    playbookIcon.setLayoutParams(layout);

                    constraintLayout.addView(playbookIcon);

                    ConstraintSet set = new ConstraintSet();
                    set.clone(constraintLayout);

                    set.connect(playbookIcon.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                    set.connect(playbookIcon.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                    set.connect(playbookIcon.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                    set.connect(playbookIcon.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
                    set.setVerticalBias(playbookIcon.getId(), 0.15f+(0.05f*(float)(footballAmt+playbookAmt)));
                    set.setHorizontalBias(playbookIcon.getId(), 0.956f);
                    set.applyTo(constraintLayout);

                    playbookIcon.startAnimation(animation);
                }
            });
        }
    }

    private class PlaybookThread extends Thread{
        public void run(){
            new Timer().scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    total.getAndAdd(5);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            totalText.setText(total.toString()+" Touchdowns");
                        }
                    });
                }
            }, 1000, 1000);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("total", total.get());
        savedInstanceState.putInt("passive", passive);
        savedInstanceState.putInt("footballAmt", footballAmt);
        savedInstanceState.putInt("footballPrice", footballPrice);
        savedInstanceState.putInt("playbookAmt", playbookAmt);
        savedInstanceState.putInt("playbookPrice", playbookPrice);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        total.set(savedInstanceState.getInt("passive"));
        passive = savedInstanceState.getInt("passive");
        footballAmt = savedInstanceState.getInt("footballAmt");
        footballPrice = savedInstanceState.getInt("footballPrice");
        playbookAmt = savedInstanceState.getInt("playbookAmt");
        playbookPrice = savedInstanceState.getInt("playbookPrice");

        totalText.setText(total.toString()+" Touchdowns");
        passiveText.setText(passive+" cps");
        footballText.setText("Footballs: "+footballAmt+"\nPrice: "+footballPrice);
        playbookText.setText("Playbooks: "+playbookAmt+"\nPrice: "+playbookPrice);

        Toast.makeText(MainActivity.this, "Progress Restored", Toast.LENGTH_SHORT).show();
    }
}