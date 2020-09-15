package com.msapp.shoottheenemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Missile {
    int x,y;
    int mVelocity;
    Bitmap missile;
    public Missile(Context context) {
        missile= BitmapFactory.decodeResource(context.getResources(),R.drawable.missile3);
        x=GameView.dWidth/2-getMissileWidth()/2;
        y=GameView.dHeight-GameView.shooterHeight-getMissileHeight()/2;
        mVelocity=60;
    }

    public int getMissileWidth(){
        return missile.getWidth();
    }
    public int getMissileHeight(){
        return missile.getHeight();
    }
}
