package com.shannor.theloyaltynetwork.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shannor on 10/24/2015.
 * Class created to hold the User information that will be displayed on the post.
 * This Class will most likely be expanded in the future.
 */
public class User extends Entity {
    private List<Group> affiliations = new ArrayList<>();

    public User(String name){
        this.name = name;
        this.mission = null;
        this.affiliations = new ArrayList<>();
        this.postList = new ArrayList<>();
        this.totalPointsEarned = 0;
    }

    public void addAffiliation(List<Group> affiliations){
        this.affiliations.addAll(affiliations);
    }
    public void addAffiliation(Group affiliation){
        this.affiliations.add(affiliation);
    }
    public List<Group> getAffiliations(){
        return this.affiliations;
    }
}
