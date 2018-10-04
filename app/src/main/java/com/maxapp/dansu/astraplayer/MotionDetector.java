package com.maxapp.dansu.astraplayer;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Dansu on 11.02.2017.
 */

public class MotionDetector {
    private float initialX, initialY;
    private long tapTime = 1000;
    public String move(MotionEvent event){

        int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                initialY = event.getY();

                if((System.currentTimeMillis() - tapTime) <= 700){
                    tapTime = System.currentTimeMillis();
                    return "doubleTap";
                }
                else
                    tapTime = System.currentTimeMillis();

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                float finalY = event.getY();

                if (initialX+150 < finalX) {
                    return "right";
                }

                if (initialX-150 > finalX) {
                    return "left";
                }

                if (initialY+200 < finalY) {
                    return "down";
                }

                if (initialY-200 > finalY) {
                    return "up";
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_OUTSIDE:
                break;
        }
        return "none";
    }
}
