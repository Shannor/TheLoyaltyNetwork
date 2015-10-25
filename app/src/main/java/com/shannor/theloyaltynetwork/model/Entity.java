package com.shannor.theloyaltynetwork.model;

import java.util.List;

/**
 * Created by Shannor on 10/25/2015.
 * Master Abstract class that Group and User extend from
 */
public abstract class Entity {
    protected String name;
    protected int totalPointsEarned;
    protected List<Post> postList;
    protected String mission;

    public void increaseTotalPointsEarned(int newPoints){
        this.totalPointsEarned += newPoints;
    }
    public void decreaseTotlaPointsEarned(int newPoints){
        if(totalPointsEarned > 0 && (totalPointsEarned - newPoints >0)){
            //Have enough points to remove
            totalPointsEarned -= newPoints;
        }else{
            //Removing points would put you below zero and zero is the min
            totalPointsEarned = 0;
        }
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void changeMission(String newMission){
        this.mission = newMission;
    }
    public boolean addPost(Post post){
       return postList.add(post);
    }
    public boolean removePost(Post post){
       return postList.remove(post);
    }
    public void changeName(String name){
        this.name = name;
    }
}
