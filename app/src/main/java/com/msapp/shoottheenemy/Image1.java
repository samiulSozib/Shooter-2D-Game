package com.msapp.shoottheenemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import static com.msapp.shoottheenemy.GameView.dWidth;

public class Image1 {
    Bitmap[] image1=new Bitmap[1];
    int image1X,image1Y,image1Velocity,image1Frame;
    Random random;

    public Image1(Context context) {
        image1[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.animalone);
        random=new Random();

        resetPosition();
    }

    public Bitmap getBitmap(){
        return image1[image1Frame];
    }
    public int getWidth(){
        return image1[0].getWidth();
    }
    public int getHeight(){
        return image1[0].getHeight();
    }

    public void resetPosition(){
        if (GameView.level==1){
            image1Velocity=8+random.nextInt(13);
        }else if (GameView.level==2){
            image1Velocity=13+random.nextInt(13);
        }else if (GameView.level==3){
            image1Velocity=18+random.nextInt(13);
        }
        image1X=dWidth+random.nextInt(1200);
        image1Y=random.nextInt(300);
        //image1Velocity=10+random.nextInt(10);
        image1Frame=0;
    }
}
