package com.msapp.shoottheenemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Image4 extends Image1 {
    Bitmap[] image1=new Bitmap[1];
    public Image4(Context context) {
        super(context);

        image1[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.animalfour);
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
            image1Velocity=8+random.nextInt(13);
        }else if (GameView.level==2){
            image1Velocity=13+random.nextInt(13);
        }else if (GameView.level==3){
            image1Velocity=18+random.nextInt(13);
        }
        image1X=-(200+random.nextInt(2100));
        image1Y=random.nextInt(350);
        //image1Velocity=5+random.nextInt(15);
        image1Frame=0;
    }
}
