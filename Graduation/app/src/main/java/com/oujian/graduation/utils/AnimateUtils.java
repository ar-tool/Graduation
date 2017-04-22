package com.oujian.graduation.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by yi on 2017/03/28.
 * 增加一些动画效果
 */

public class AnimateUtils {
    public static void startScaleAnimate(final View view){
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(0.0f)
                .setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                                .alpha(1.0f)
                                .setDuration(100);
                    }
                });
    }
}
