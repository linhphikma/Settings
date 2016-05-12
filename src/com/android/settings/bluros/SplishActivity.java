package com.android.settings.bluros;

import android.app.Activity;
import android.graphics.Typeface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.android.settings.bluros.MainSettings;
import com.android.settings.*;
import com.android.settings.R;

import java.util.Random;


public class SplishActivity extends Activity {

    private Animation mFadeIn;
    private Animation mFadeInScale;
    private Animation mFadeOut;

    //  @InjectView(R.id.image)
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish);
        mImageView = (ImageView) findViewById(R.id.image);
        int index = new Random().nextInt(2);
        if (index == 1) {
            mImageView.setImageResource(R.drawable.entrance3);
        } else {
            mImageView.setImageResource(R.drawable.entrance2);
        }

        initAnim();
        setListener();
    }

    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in);
        mFadeIn.setDuration(500);
        mFadeInScale = AnimationUtils.loadAnimation(this,
                R.anim.welcome_fade_in_scale);
        mFadeInScale.setDuration(2000);
        mFadeOut = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_out);
        mFadeOut.setDuration(500);
        mImageView.startAnimation(mFadeIn);
    }

    public void setListener() {

        mFadeIn.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplishActivity.this, Settings.class);
                startActivity(intent);
                SplishActivity.this.finish();
            }
        });
        mFadeOut.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                // startActivity(MainActivity.class);
            }
        });
    }
}
