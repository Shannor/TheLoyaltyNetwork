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
    private int myPostID;
    static private int postID = 0; //Class level variable that will increase as more posts are made

    public Post(Entity creator,String title,String body){
        this.title = title;
        this.creator = creator;
        this.body = body;
        this.time = calculateTime();
        this.myPostID = postID++;
    }

    public String getTitle(){
        return this.title;
    }
    public String getBody(){
        return this.body;
    }
    public String getTime(){
        return this.time;
    }
    public Entity getCreator(){
        return this.creator;
    }
    public int getMyPostID(){
        return this.myPostID;
    }

    private String calculateTime(){
        String timeOFDay;
        String minute;
        Calendar cal = Calendar.getInstance();

        Integer Hour = cal.get(Calendar.AM_PM) + cal.get(Calendar.HOUR);
        Integer Minute =  cal.get(Calendar.MINUTE);
        if(Minute < 10){
            minute = "0" + Minute.toString();
        }else{
            minute = Minute.toString();
        }
        if(cal.get(Calendar.HOUR_OF_DAY) > 12){
            timeOFDay = " PM";
        }else{
            timeOFDay = " AM";
        }

        return Hour.toString() + ":" + minute + timeOFDay;

    }

}
