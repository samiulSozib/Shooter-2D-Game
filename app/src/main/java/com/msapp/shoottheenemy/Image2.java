package com.msapp.shoottheenemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.msapp.shoottheenemy.GameView.dWidth;

public class Image2 extends Image1 {

    Bitmap[] image1=new Bitmap[1];

    public Image2(Context context) {
        super(context);
        image1[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.animaltwo);
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
            image1Velocity=13+random.nextInt(18);
        }else if (GameView.level==2){
            image1Velocity=18+random.nextInt(18);
        }else if (GameView.level==3){
            image1Velocity=23+random.nextInt(18);
        }
        image1X=dWidth+random.nextInt(1500);
        image1Y=random.nextInt(350);
        //image1Velocity=5+random.nextInt(10);
        image1Frame=0;
    }
}
