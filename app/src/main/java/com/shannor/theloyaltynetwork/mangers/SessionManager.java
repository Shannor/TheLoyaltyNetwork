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

    //Key for login status
    private static final String IS_LOGIN = "isLoggedIn";

    //Save this, but Use Email to find user name with database
    public static final String KEY_NAME = "name";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_USER_OBJECT = "user";


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
        editor.clear();
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }

    /**
     * Method to set the Current User name
     * MMakes sure that email has already been set
     * @param name current User Name
     */
    public void setUserName(String name){
        if (pref.getString(KEY_EMAIL,null) != null){
            editor.putString(KEY_NAME,name);
            editor.commit();
        }
    }

    /**
     * Method to add the User object to shard Pref.
     * @param userObject current User object
     */
    public void setUserObject(User userObject){
        Gson gson = new Gson();
        String json = gson.toJson(userObject); // myObject - instance of MyObject
        editor.putString(KEY_USER_OBJECT, json);
        editor.commit();
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
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Getter to get the User Object Saved.
     * @return Current User
     */
    public User getUser(){
        Gson gson = new Gson();
        String json = pref.getString(KEY_USER_OBJECT,null);
        return gson.fromJson(json, User.class);
    }
}
