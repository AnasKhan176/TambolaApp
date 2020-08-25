package com.uae.tambolaapp.helper_classes;

import android.app.Activity;
import android.os.Build;
import android.view.View;

public class Utils {

    public static void requestFullScreen(Activity mActivity) {

        if (Build.VERSION.SDK_INT > 16) {
            View decorView = mActivity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
