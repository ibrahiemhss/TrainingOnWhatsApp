package com.example.ibrahim.lasttrainingudacity.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ibrahim on 30/12/17.
 */

public class SharedPrefManager {


    private static final String NAME_USERS_KEY = "name_users";
    private static final String REPLY_KEY = "reply";
    private static final String ON_KEY="on" ;


    private static final String SHARED_PREF_NAME = "save_contents";
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPrefManager (Context context) {
        mCtx = context;
        pref=mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);

        }
        return mInstance;
    }



    public  boolean saveNamesOfUsers( String name) {
        editor = pref.edit();
        editor.putString( NAME_USERS_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getNamesOfUsers() {

        return pref.getString( NAME_USERS_KEY, null );

    }
    public  boolean saveReply( String name) {
        editor = pref.edit();
        editor.putString( REPLY_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getPeply() {

        return pref.getString( REPLY_KEY, null );

    }
    public  boolean saveOn( int name) {
        editor = pref.edit();
        editor.putInt ( ON_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public int getOn() {

        return pref.getInt ( ON_KEY, 0 );

    }
}