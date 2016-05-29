package com.shannor.theloyaltynetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Shannor on 10/25/2015.
 * Object that will be passed into the Post Adapter and ViewHolder
 * Holds the information for what a post needs
 */
public class Post {

    private String title;
    private String body;
    private String uID; // For Users and Groups
    private String userName; // Groups and Users Name
    private long myPostID;
    private int agree = 0;
    private int disagree = 0;
    static private long postID = 0; //Class level variable that will increase as more posts are made
    private long timeStamp;

    public Post(String userName,String title,String body){

        this.userName = userName;
        this.title = title;
        this.body = body;
        this.myPostID = postID++;
        this.agree = 0;
        this.disagree = 0;
    }

    public Post(String userName ,String uID, String title,String body){

        this.userName = userName;
        this.uID = uID;
        this.title = title;
        this.body = body;
        this.myPostID = postID++;
        this.agree = 0;
        this.disagree = 0;
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

    @JsonIgnore
    public long getLongTimeStamp(){
        return this.timeStamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setMyPostID(long myPostID) {
        this.myPostID = myPostID;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public void setDisagree(int disagree) {
        this.disagree = disagree;
    }

    public static void setPostID(long postID) {
        Post.postID = postID;
    }

    public long getMyPostID(){
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

    public static long getPostID() {
        return postID;
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
