package com.shannor.theloyaltynetwork.mangers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shannor.theloyaltynetwork.activities.LoginActivity;
import com.shannor.theloyaltynetwork.model.User;

/**
 * Created by Shannor on 1/7/2016.
 * Class that will hold SharedPref's for when the user logs in.
 * Can be expanded to hold more information about the User.
 */
public class SessionManager {

    //Shared Preferences
    SharedPreferences pref;

    //Used to edit information in the Share Preferences
    SharedPreferences.Editor editor;

    //Context of who is asking for it
    Context mContext;

    // Shared pref Mode
    private int PRIVATE_MODE = 0;

    //Share Pref, File Name
    private static final String PREF_NAME = "UserLoginInformation";

    //Save this, but Use Email to find user name with database
    public static final String KEY_USERNAME = "userName";

    public static final String KEY_UID = "uid";

    //Constructor
    public SessionManager(Context context){
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Method to set the Current User name
     * MMakes sure that email has already been set
     * @param name current User Name
     */
    public void setUsername(String name){
        editor.putString(KEY_USERNAME,name);
        editor.commit();
    }

    public void setUid(String uid){
        editor.putString(KEY_UID,uid);
        editor.commit();
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME,null);
    }

    public String getUid() {
        return pref.getString(KEY_UID,null);
    }


    /**
     * Clears the Users information,
     * then redirect them to the Login Activity.
     */
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }
}
