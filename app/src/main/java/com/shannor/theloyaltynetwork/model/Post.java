package com.shannor.theloyaltynetwork.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by Shannor on 10/25/2015.
 * Object that will be passed into the Post Adapter and ViewHolder
 * Holds the information for what a post needs
 */
//TODO: Make it so the time shows the difference since being posted ie(same day = 6:40, different day 10/21/2016)
public class Post {

    private String title;
    private String body;
    private String uID; // For Users and Groups
    private String userName; // Groups and Users Name
    private String myPostID;
    private int agree = 0;
    private int disagree = 0;
    private long timeStamp;
    private List<Post> replies;

    public Post(){
        //Empty Constructor for Firebase
    }
    public Post(String userName,String title,String body){

        this.userName = userName;
        this.title = title;
        this.body = body;
        this.agree = 0;
        this.disagree = 0;
        this.replies = new ArrayList<>();
    }

    public Post(String userName ,String uID, String title,String body){

        this.userName = userName;
        this.uID = uID;
        this.title = title;
        this.body = body;
        this.agree = 0;
        this.disagree = 0;
        this.replies = new ArrayList<>();
    }

    public int getAgree(){return this.agree;}
    public int getDisagree(){return this.disagree;}
    public String getTitle(){
        return this.title;
    }
    public String getBody(){
        return this.body;
    }


    public Map<String, String> getTimeStamp() {
        return ServerValue.TIMESTAMP;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<Post> getReplies() {
        return replies;
    }

    public void setReplies(List<Post> replies) {
        this.replies = replies;
    }

    @Exclude

    public long getLongTimeStamp(){
        return this.timeStamp;
    }

    @Exclude
    public String getTime(){
        SimpleDateFormat compareFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date today = new Date();
        Date postTime = new Date(getLongTimeStamp());
        //Compare if Post was made in the same day
        if(compareFormat.format(today).equals(compareFormat.format(postTime))) {
            return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(postTime);
        }else{
            return new SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(postTime);
        }
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setMyPostID(String myPostID) {
        this.myPostID = myPostID;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public void setDisagree(int disagree) {
        this.disagree = disagree;
    }

    public String getMyPostID(){
        return this.myPostID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void increaseAgree(){
        agree++;
    }
    public void increaseDisagree(){
        disagree++;
    }

    @Exclude
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        map.put("uID",uID);
        map.put("title",title);

        return map;
    }

}
