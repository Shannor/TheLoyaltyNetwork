package com.shannor.theloyaltynetwork.mangers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.shannor.theloyaltynetwork.activities.LoginActivity;

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

    //Key for login status
    private static final String IS_LOGN = "isLoggedIn";

    //May not be used, is for if we want to store the User's name
    public static final String KEY_NAME = "name";

    public static final String KEY_EMAIL = "email";

    //Constructor
    public SessionManager(Context context){
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Method to start the Login session and save the email.
     * @param email User's personal Email
     */
    public void createLoginSession(String email){

        editor.putBoolean(IS_LOGN,true);
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }

    /**
     * Method to check if User is logged in.
     * If not will start the Login Activity
     */
    public void checkLogin(){
        if (!this.isLoggedIn()){
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }
    }

    /**
     * Clears the Users information,
     * then redirect them to the Login Activity.
     */

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(mContext,LoginActivity.class);
        mContext.startActivity(intent);
    }
    /**
     * Gets the Users Email.
     * If expand in future can change to a HashMap and return more information,
     * about the User.
     * @return User Email or null.
     */
    public String getUserEmail(){
        return pref.getString(KEY_EMAIL,null);
    }

    /**
     * Getter to check the login status.
     * @return true or false.
     */
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGN,false);
    }
}
