package com.shannor.theloyaltynetwork.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shannor on 10/24/2015.
 * Class created to hold the User information that will be displayed on the post.
 * This Class will most likely be expanded in the future.
 */
public class User {
    private List<Group> affiliations;
    private String name;
    private long totalPointsEarned;
    private List<Post> postList;
    private String mission;

    public User(){
        //Needed for Firebase
    }
    public User(String name){
        this.name = name;
        this.mission = null;
        this.affiliations = new ArrayList<>(); //Group ID's
        this.postList = new ArrayList<>(); //Post ID's
        this.totalPointsEarned = 0;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public void setTotalPointsEarned(long totalPointsEarned) {
        this.totalPointsEarned = totalPointsEarned;
    }

    public List<Group> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<Group> affiliations) {
        this.affiliations = affiliations;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
