package com.shannor.theloyaltynetwork.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shannor on 10/25/2015.
 * Group is a collection of Users
 * Will have levels of members and some attributes that Users also has
 * Rules of moving up,
 * Anyone can Follow a Group
 * To become a member must have a certain number of points with this group
 * To become an admin must have a certain number of points and approved by the Leader and Majority admins
 * Leader can be changed by Democratic vote from Admins and Members(.5 per vote)
 */
public class Group extends Entity {
    //Leader can pick the amount of points needed to move up just cannot exceed the max
    final static private int MAX_MEMBER_REQUIREMENTS = 2000;
    final static private int MAX_ADMIN_REQUIREMENTS = 4000;
    private List<User> members;
    private User leader;
    private List<User> admins;
    private List<User> followers;

    //Must have a leader when creating the group
    public Group(User leader,String name, String mission){
        this.leader = leader;
        this.name = name;
        this.mission = mission;
        this.totalPointsEarned = 0;
        this.postList = new LinkedList<>();
        this.members = new LinkedList<>();
        this.admins = new LinkedList<>();
        this.followers = new LinkedList<>();
    }

    public void addMember(List<User> newMembers){
        this.members.addAll(newMembers);
    }
    public void addMember(User newMember){
        this.members.add(newMember);
    }
    public boolean makeAdmin(User newAdmin){
        if(members.contains(newAdmin)){
            admins.add(newAdmin);
            return true;
        }
        //Cannot add user that is not a member of the group
        return false;
    }
    public void addFollower(User newFollower){
        this.followers.add(newFollower);
    }
    public void addFollower(List<User> newFollowers){
        this.followers.addAll(newFollowers);
    }

}
