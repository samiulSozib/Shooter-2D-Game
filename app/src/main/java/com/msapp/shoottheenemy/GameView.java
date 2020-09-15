package com.msapp.shoottheenemy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;


public class GameView extends View {

    ///
    InterstitialAd  interstitial;
    AdRequest adRequest1;
    ///
    Bitmap background, shooter, background2,background3;
    Rect rect;
    static int dHeight, dWidth;
    ArrayList<Image1> image1s, image1s2, image1s3, image1s4;
    ArrayList<Missile> missiles;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    Paint scorePaint,healthPaint,borderPaint;
    static int shooterWidth, shooterHeight;
    Context context;
    int count = 0;
    int score = 0;
    SoundPool soundPool;
    int fire = 0, point = 0;
    int life=20;
    static int level=1;
    Typeface typeface;
    private int enimy=0;

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        background2=BitmapFactory.decodeResource(getResources(),R.drawable.back1);
        background3=BitmapFactory.decodeResource(getResources(),R.drawable.back3);

        shooter = BitmapFactory.decodeResource(getResources(), R.drawable.shooter2);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dHeight = size.y;
        dWidth = size.x;

        rect = new Rect(0, 0, dWidth, dHeight);
        image1s = new ArrayList<>();
        image1s2 = new ArrayList<>();
        image1s3 = new ArrayList<>();
        image1s4 = new ArrayList<>();

        missiles = new ArrayList<>();

        Image1 image1 = new Image1(context);
        image1s.add(image1);

        Image2 image2 = new Image2(context);
        image1s2.add(image2);

        Image3 image3 = new Image3(context);
        image1s3.add(image3);

        Image4 image4 = new Image4(context);
        image1s4.add(image4);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        scorePaint = new Paint();
        scorePaint.setColor(getResources().getColor(R.color.scoreColor));
        scorePaint.setTextSize(60);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        scorePaint.setStrokeWidth(3f);
        typeface=Typeface.createFromAsset(context.getAssets(),"lesotho_beach.otf");
        scorePaint.setTypeface(typeface);

        shooterWidth = shooter.getWidth();
        shooterHeight = shooter.getHeight();

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        fire = soundPool.load(context, R.raw.fire, 1);
        point = soundPool.load(context, R.raw.point, 1);

        healthPaint=new Paint();
        healthPaint.setColor(Color.GREEN);

        borderPaint=new Paint();
        borderPaint.setColor(Color.RED);
        borderPaint.setStrokeWidth(3f);

        /// intestatioal ads
        MobileAds.initialize(context,"ca-app-pub-3940256099942544~3347511713");  // app publisher id
        adRequest1=new AdRequest.Builder().build();

        interstitial=new InterstitialAd(context);
        interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//// interstisial add id
        //interstitial.loadAd(adRequest1);

        interstitial.setAdListener(new AdListener(){

            public void onAdLoaded(){
                displayInterstitial();
            }

        });

        ///

    }

    private void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //// interstitial ads
        if (enimy==500){
            interstitial.loadAd(adRequest1);
        }
        //// interstitial ads

        //leveling the game
        if (count>10 && count<21){
            level=2;
        }else if (count>=21){
            level=3;
        }

        if (count<=10){
            canvas.drawBitmap(background, null, rect, null);
        }else if(count<21){
            canvas.drawBitmap(background2, null, rect, null);
        }else{
            canvas.drawBitmap(background3, null, rect, null);
        }


        //leveling the game

        //canvas.drawBitmap(background, null, rect, null);
        for (int i = 0; i < image1s.size(); i++) {
            // drawing image one
            canvas.drawBitmap(image1s.get(i).getBitmap(), image1s.get(i).image1X, image1s.get(i).image1Y, null);
            image1s.get(i).image1Frame++;
            enimy++;
            if (image1s.get(i).image1Frame > 0) {
                image1s.get(i).image1Frame = 0;
            }
            image1s.get(i).image1X -= image1s.get(i).image1Velocity;
            if (image1s.get(i).image1X < -image1s.get(i).getWidth()) {
                image1s.get(i).resetPosition();
                life--;
                if (life == 0){

                    Intent intent;
                    intent=new Intent(context,GameOverActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                }
            }
            // drawing image two

            canvas.drawBitmap(image1s2.get(i).getBitmap(), image1s2.get(i).image1X, image1s2.get(i).image1Y, null);
            image1s2.get(i).image1Frame++;
            if (image1s2.get(i).image1Frame > 0) {
                image1s2.get(i).image1Frame = 0;
            }
            image1s2.get(i).image1X -= image1s2.get(i).image1Velocity;
            if (image1s2.get(i).image1X < -image1s2.get(i).getWidth()) {
                image1s2.get(i).resetPosition();
                life--;
                if (life == 0){

                    Intent intent;
                    intent=new Intent(context,GameOverActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                }
            }
            // drawing image three

            canvas.drawBitmap(image1s3.get(i).getBitmap(), image1s3.get(i).image1X, image1s3.get(i).image1Y, null);
            image1s3.get(i).image1Frame++;
            if (image1s3.get(i).image1Frame > 0) {
                image1s3.get(i).image1Frame = 0;
            }
            image1s3.get(i).image1X += image1s3.get(i).image1Velocity;
            if (image1s3.get(i).image1X > (dWidth + image1s3.get(i).getWidth())) {
                image1s3.get(i).resetPosition();
                life--;
                if (life == 0){

                    Intent intent;
                    intent=new Intent(context,GameOverActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                }
            }
            // drawing image four

            canvas.drawBitmap(image1s4.get(i).getBitmap(), image1s4.get(i).image1X, image1s4.get(i).image1Y, null);
            image1s4.get(i).image1Frame++;
            if (image1s4.get(i).image1Frame > 0) {
                image1s4.get(i).image1Frame = 0;
            }
            image1s4.get(i).image1X += image1s4.get(i).image1Velocity;
            if (image1s4.get(i).image1X > (dWidth + image1s4.get(i).getWidth())) {
                image1s4.get(i).resetPosition();
                life--;
                if (life == 0){

                    Intent intent;
                    intent=new Intent(context,GameOverActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                }
            }
        }

        for (int i = 0; i < missiles.size(); i++) {
            if (missiles.get(i).y > -missiles.get(i).getMissileHeight()) {
                missiles.get(i).y -= missiles.get(i).mVelocity;
                canvas.drawBitmap(missiles.get(i).missile, missiles.get(i).x, missiles.get(i).y, null);
                if (missiles.get(i).x >= image1s.get(0).image1X && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (image1s.get(0).image1X + image1s.get(0).getWidth()) && missiles.get(i).y >= image1s.get(0).image1Y
                        && missiles.get(i).y <= (image1s.get(0).image1Y + image1s.get(0).getHeight())) {
                    image1s.get(0).resetPosition();
                    count++;
                    score += 10;
                    missiles.remove(i);
                    if (point != 0) {
                        soundPool.play(point, 1, 1, 0, 0, 1);
                    }
                } else if (missiles.get(i).x >= image1s2.get(0).image1X && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (image1s2.get(0).image1X + image1s2.get(0).getWidth()) && missiles.get(i).y >= image1s2.get(0).image1Y
                        && missiles.get(i).y <= (image1s2.get(0).image1Y + image1s2.get(0).getHeight())) {
                    image1s2.get(0).resetPosition();
                    count++;
                    score += 15;
                    missiles.remove(i);
                    if (point != 0) {
                        soundPool.play(point, 1, 1, 0, 0, 1);
                    }
                } else if (missiles.get(i).x >= image1s3.get(0).image1X && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (image1s3.get(0).image1X + image1s3.get(0).getWidth()) && missiles.get(i).y >= image1s3.get(0).image1Y
                        && missiles.get(i).y <= (image1s3.get(0).image1Y + image1s3.get(0).getHeight())) {
                    image1s3.get(0).resetPosition();
                    count++;
                    score += 20;
                    missiles.remove(i);
                    if (point != 0) {
                        soundPool.play(point, 1, 1, 0, 0, 1);
                    }
                } else if (missiles.get(i).x >= image1s4.get(0).image1X && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (image1s4.get(0).image1X + image1s4.get(0).getWidth()) && missiles.get(i).y >= image1s4.get(0).image1Y
                        && missiles.get(i).y <= (image1s4.get(0).image1Y + image1s4.get(0).getHeight())) {
                    image1s4.get(0).resetPosition();
                    count++;
                    score += 25;
                    missiles.remove(i);
                    if (point != 0) {
                        soundPool.play(point, 1, 1, 0, 0, 1);
                    }
                }
            } else {
                missiles.remove(i);
            }
        }



        canvas.drawText("Score : " + score, 20, 60, scorePaint);
        canvas.drawRect(dWidth-110,10,dWidth-110+5*life,60,healthPaint);
        canvas.drawLine(dWidth-110,10,dWidth-10,10,borderPaint);
        canvas.drawLine(dWidth-110,60,dWidth-10,60,borderPaint);
        canvas.drawLine(dWidth-110,10,dWidth-110,60,borderPaint);
        canvas.drawLine(dWidth-10,10,dWidth-10,60,borderPaint);
        canvas.drawBitmap(shooter, ((float) dWidth / 2 - (float) shooterWidth / 2), dHeight - shooterHeight, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (touchX >= ((float) dWidth / 2 - (float) shooterWidth / 2) && touchX <= ((float) dWidth / 2 + (float) shooterWidth / 2) && touchY >= (dHeight - shooterHeight)) {
                //Toast.makeText(getContext(),"Touched",Toast.LENGTH_SHORT).show();
                if (missiles.size() < 5) {
                    Missile missile = new Missile(context);
                    missiles.add(missile);
                    if (fire != 0) {
                        soundPool.play(fire, 1, 1, 0, 0, 1);
                    }
                }
            }

        }
        return true;
    }
}
