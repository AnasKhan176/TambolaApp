package com.uae.tambolaapp.helper_classes;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {
    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String gameID = "g_id",login_flag="l_status";

    public AppPrefs(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS,
                Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public long getGameID() {
        return appSharedPrefs.getLong(gameID, 0);
    }

    public void setGameID(long no) {
        prefsEditor.putLong(gameID, no).commit();
    }

    public int getLogin() {
        return appSharedPrefs.getInt(login_flag, 0);
    }

    public void setLogin(int no) {
        prefsEditor.putInt(login_flag, no).commit();
    }


}
