package com.uae.tambolaapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.uae.tambolaapp.helper_classes.AppPrefs;
import com.uae.tambolaapp.helper_classes.StringUtils;


public class HomeActivity extends BaseActivity {
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //futureg
        setContentView(R.layout.activity_home);
        prefs = new AppPrefs(this);
    }

    public void actionPlay(View v) {
        /*Intent intent = new Intent(HomeActivity.this, VideoPlayActivity.class);
        startActivity(intent);*/
        // This will work when youtube app already install
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=I1YR7wcbDWYInboxx"));
//        startActivity(intent);
    }

    public void actionRule(View v) {
//        Intent intent = new Intent(HomeActivity.this, PrivacyTermActivity.class);
//        intent.putExtra("url_type",0);
//        startActivity(intent);
    }
    public void actionWinning(View v) {
//        Intent intent = new Intent(HomeActivity.this, PrivacyTermActivity.class);
//        intent.putExtra("url_type",1);
//        startActivity(intent);
    }
    public void actionPolicy(View v) {
//        Intent intent = new Intent(HomeActivity.this, PrivacyTermActivity.class);
//        intent.putExtra("url_type",2);
//        startActivity(intent);
    }

    public void actionPlayer(View v) {
        toastLong(this, "Play as a player");
    }

    public void actionHost(View v) {
        /*if (prefs.getLogin() > 0) {
            prefs.setGameID(prefs.getGameID() + 1);
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            startActivity(intent);
        }*/
        // Generate game id
        if (prefs.getGameID() == 0) {
            prefs.setGameID(StringUtils.getGameID());
        } else {
            prefs.setGameID(prefs.getGameID() + 1);
        }
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void actionShare(View v) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.invitation_title);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out, A simple Tambola game " +
                "https://www.google.com" + "\n");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, " Choose an app "));
    }

}
