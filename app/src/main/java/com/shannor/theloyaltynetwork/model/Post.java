package com.shannor.theloyaltynetwork.model;

import java.util.Calendar;

/**
 * Created by Shannor on 10/25/2015.
 * Object that will be passed into the Post Adapter and ViewHolder
 * Holds the information for what a post needs
 */
public class Post {

    private String title;
    private String body;
    private String time;
    private Entity creator;
    private long myPostID;
    private int agree = 0;
    private int disagree = 0;
    static private long postID = 0; //Class level variable that will increase as more posts are made

    //TODO:Add point system to Posts
    public Post(Entity creator,String title,String body){

        this.title = title;
        this.creator = creator;
        this.body = body;
        this.time = calculateTime();
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
    public String getTime(){
        return this.time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCreator(Entity creator) {
        this.creator = creator;
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

    public Entity getCreator() {
        return creator;
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
    private String calculateTime(){
        String timeOFDay;
        String minute;
        Calendar cal = Calendar.getInstance();

        Integer Hour = cal.get(Calendar.AM_PM) + cal.get(Calendar.HOUR);
        Integer Minute =  cal.get(Calendar.MINUTE);

        //Adding zero if single digit time
        if (Minute < 10){
            minute = "0" + Minute.toString();
        }else {
            minute = Minute.toString();
        }
        //AM or PM check
        if (cal.get(Calendar.HOUR_OF_DAY) > 12){
            timeOFDay = " PM";
        }else {
            timeOFDay = " AM";
        }
        //Midnight edge case
        if (Hour == 0){
            Hour = 12;
        }

        return Hour.toString() + ":" + minute + timeOFDay;


    }

}
