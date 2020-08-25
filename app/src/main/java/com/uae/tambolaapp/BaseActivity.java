package com.uae.tambolaapp;

import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    public void removeStatusBar() {
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setActivityTitle(String title) {
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);
            actionBar.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showBackButton(boolean isBackButton) {
        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(isBackButton);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void toastShort(Activity ctx, String message) {
        Toast toast = null;
        if (!ctx.isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }
            if (message != null && message.length() > 0) {
                toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void toastLong(Activity ctx, String message) {
        Toast toast = null;
        if (!ctx.isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }
            if (message != null && message.length() > 0) {
                toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
