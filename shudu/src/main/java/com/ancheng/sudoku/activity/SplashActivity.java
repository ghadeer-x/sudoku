package com.ancheng.sudoku.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.utils.IntentTools;

public class SplashActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        textView = (TextView) findViewById(R.id.tv_text);
        //设置动画
        ObjectAnimator scleaX = ObjectAnimator.ofFloat(textView, "scaleX", 5f, 4f, 3f, 2f, 1f, 0.5f, 0.1f, 1f);
        ObjectAnimator scleaY = ObjectAnimator.ofFloat(textView, "scaleY", 5f, 4f, 3f, 2f, 1f, 0.5f, 0.1f, 1f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(textView, "rotation", 0f, 720f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textView, "alpha", 0.1f, 0.2f, 0.4f, 0.6f, 0.8f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(scleaX, scleaY, rotate, fadeInOut);
        animSet.setDuration(5000);
        animSet.start();

        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                if (MyApplication.isLogin()) {
//                    Intent intent = IntentTools.getLoginActivityIntent(SplashActivity.this);
//                    startActivity(intent);
//                } else {
//                    Intent intent = IntentTools.getMainActivityIntent(SplashActivity.this);
//                    startActivity(intent);
//                }
                Intent intent = IntentTools.getLoginActivityIntent(SplashActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }
}
