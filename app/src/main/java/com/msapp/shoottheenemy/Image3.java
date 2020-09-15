package com.msapp.shoottheenemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Image3 extends Image1{
    Bitmap[] image1=new Bitmap[1];

    public Image3(Context context) {
        super(context);
        image1[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.animalthree);
        resetPosition();
    }
    @Override
    public Bitmap getBitmap() {
        return image1[image1Frame];
    }

    @Override
    public int getWidth() {
        return image1[0].getWidth();
    }

    @Override
    public int getHeight() {
        return image1[0].getHeight();
    }

    @Override
    public void resetPosition() {
        if (GameView.level==1){
            image1Velocity=15+random.nextInt(23);
        }else if (GameView.level==2){
            image1Velocity=20+random.nextInt(23);
        }else if (GameView.level==3){
            image1Velocity=24+random.nextInt(23);
        }
        image1X=-(200+random.nextInt(2100));
        image1Y=random.nextInt(400);
        //image1Velocity=5+random.nextInt(20);
        image1Frame=0;
    }
}
