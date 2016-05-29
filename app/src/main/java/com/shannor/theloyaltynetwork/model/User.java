package com.shannor.theloyaltynetwork.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Shannor on 10/24/2015.
 * Class created to hold the User information that will be displayed on the post.
 * This Class will most likely be expanded in the future.
 */
public class User {

    private List<String> affiliations;
    private String name;
    private String uID;
    private long totalPointsEarned;
    private List<String> postList;
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

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setTotalPointsEarned(long totalPointsEarned) {
        this.totalPointsEarned = totalPointsEarned;
    }

    public List<String> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<String> affiliations) {
        this.affiliations = affiliations;
    }

    public List<String> getPostList() {
        return postList;
    }

    public void setPostList(List<String> postList) {
        this.postList = postList;
    }


    @Exclude
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();

        map.put("name",name);
        map.put("mission",mission);
        map.put("uID",uID);
        map.put("totalPointsEarned",totalPointsEarned);
        map.put("affiliations",affiliations);
        map.put("postList",postList);

        return map;
    }
}
