package com.maxapp.dansu.astraplayer;

import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.maxapp.dansu.astraplayer.MusicService.DataHolder;

public class ImageAnimator {
    private int startx;
    private int starty;
    private int imgDefaultX;
    private int imgDefaultY;
    private DataHolder dh;
    ImageAnimator(int defX, int defY, DataHolder dh){
        imgDefaultX = defX;
        imgDefaultY = defY;
        this.dh = dh;
    }

    public void animate(MotionEvent event, final ImageView imgView){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startx = (int)event.getRawX();
                starty = (int)event.getRawY();
                ConstraintLayout.LayoutParams tmpParams = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
                tmpParams.topMargin = imgDefaultY;
                tmpParams.leftMargin = imgDefaultX;
                imgView.setLayoutParams(tmpParams);



            case MotionEvent.ACTION_MOVE:
                ConstraintLayout.LayoutParams mParams = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                mParams.leftMargin = imgDefaultX - (startx - x);
                mParams.topMargin = imgDefaultY - (starty - y);
                imgView.setLayoutParams(mParams);
                break;

            default:
                break;
        }
    }

    public void animateRight(Context ctx, final ImageView imgView){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
        final int deltaY = imgDefaultY - params.topMargin;
        final int topMargin = params.topMargin;
        final int leftMargin = params.leftMargin;

        Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
                params.topMargin = (int)(topMargin + (deltaY * interpolatedTime));
                params.leftMargin = (int)(leftMargin + (1500 * interpolatedTime));
                imgView.setLayoutParams(params);
            }
        };
        a.setDuration(500); // in ms
        a.setInterpolator(ctx, android.R.anim.overshoot_interpolator);
        imgView.startAnimation(a);
        respawnImage(ctx, imgView);
    }

    public void animateLeft(Context ctx, final ImageView imgView){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
        final int deltaY = imgDefaultY - params.topMargin;
        final int topMargin = params.topMargin;
        final int rightMargin = params.rightMargin;

        Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
                params.topMargin = (int)(topMargin + (deltaY * interpolatedTime));
                params.rightMargin = (int)(rightMargin + (1500 * interpolatedTime));
                imgView.setLayoutParams(params);
            }
        };
        a.setDuration(500); // in ms
        a.setInterpolator(ctx, android.R.anim.overshoot_interpolator);
        imgView.startAnimation(a);
        respawnImage(ctx, imgView);
    }

    public void animateDown(Context ctx, final ImageView imgView){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
        final int deltaX = imgDefaultX - params.leftMargin;
        final int topMargin = params.topMargin;
        final int rightMargin = params.rightMargin;

        Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
                params.topMargin = (int)(topMargin + (1500 * interpolatedTime));
                params.rightMargin = (int)(rightMargin + (deltaX * interpolatedTime));
                imgView.setLayoutParams(params);
            }
        };
        a.setDuration(500); // in ms
        a.setInterpolator(ctx, android.R.anim.overshoot_interpolator);
        imgView.startAnimation(a);
        respawnImage(ctx, imgView);
    }

    public void animateUp(Context ctx, final ImageView imgView){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
        final int deltaX = imgDefaultX - params.leftMargin;
        final int bottomMargin = params.bottomMargin;
        final int rightMargin = params.rightMargin;

        Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
                params.bottomMargin = (int)(bottomMargin + (2500 * interpolatedTime));
                params.rightMargin = (int)(rightMargin + (deltaX * interpolatedTime));
                imgView.setLayoutParams(params);
            }
        };
        a.setDuration(500); // in ms
        a.setInterpolator(ctx, android.R.anim.overshoot_interpolator);
        imgView.startAnimation(a);
        respawnImage(ctx, imgView);
    }

    private void respawnImage(final Context ctx, final ImageView imgView){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
                params.topMargin = imgDefaultY;
                params.leftMargin = imgDefaultX;
                params.rightMargin = imgDefaultX;
                params.bottomMargin = 0;
                imgView.setLayoutParams(params);
                imgView.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_in));
                imgView.setImageBitmap(dh.getSongImage());
            }
        }, 300);
    }
}
