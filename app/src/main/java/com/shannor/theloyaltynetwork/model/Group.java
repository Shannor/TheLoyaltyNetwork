package com.shannor.theloyaltynetwork.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class Group {
    //Leader can pick the amount of points needed to move up just cannot exceed the max
    private String groupName;
    private String groupID;
    private int totalPointsEarned;
    private List<String> postList;
    private String mission;
    private List<String> membersIDs;
    private String leaderID;
    private List<String> adminsIDs;
    private List<String> followersIDs;
    private int requirementForMember; // Will have defaults they can change later
    private int requirementForAdmin;

    public Group(){
        //Empty Constructor needed for firebase
    }

    //Must have a leader when creating the group
    public Group(String leaderID,String groupName, String mission){
        this.leaderID = leaderID;
        this.groupName = groupName;
        this.mission = mission;
        this.totalPointsEarned = 0;
        this.postList = new ArrayList<>();
        this.membersIDs = new ArrayList<>();
        this.adminsIDs = new ArrayList<>();
        this.followersIDs = new ArrayList<>();
        this.requirementForMember = 2000; //Default
        this.requirementForAdmin = 4000; //Default
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public void setTotalPointsEarned(int totalPointsEarned) {
        this.totalPointsEarned = totalPointsEarned;
    }

    public List<String> getPostList() {
        return postList;
    }

    public void setPostList(List<String> postList) {
        this.postList = postList;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public List<String> getMembersIDs() {
        return membersIDs;
    }

    public void setMembersIDs(List<String> membersIDs) {
        this.membersIDs = membersIDs;
    }

    public String getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(String leaderID) {
        this.leaderID = leaderID;
    }

    public List<String> getAdminsIDs() {
        return adminsIDs;
    }

    public void setAdminsIDs(List<String> adminsIDs) {
        this.adminsIDs = adminsIDs;
    }

    public List<String> getFollowersIDs() {
        return followersIDs;
    }

    public void setFollowersIDs(List<String> followersIDs) {
        this.followersIDs = followersIDs;
    }

    public int getRequirementForMember() {
        return requirementForMember;
    }

    public void setRequirementForMember(int requirementForMember) {
        this.requirementForMember = requirementForMember;
    }

    public int getRequirementForAdmin() {
        return requirementForAdmin;
    }

    public void setRequirementForAdmin(int requirementForAdmin) {
        this.requirementForAdmin = requirementForAdmin;
    }

    @Exclude
    public boolean addMembers(List<String> newMembers){
        if (membersIDs != null) {
            return this.membersIDs.addAll(newMembers);
        }else{
            membersIDs = new ArrayList<>();
           return membersIDs.addAll(newMembers);
        }
    }

    @Exclude
    public boolean addMember(String newMember){
        if( membersIDs != null) {
            return this.membersIDs.add(newMember);
        }else{
            membersIDs = new ArrayList<>();
            return membersIDs.add(newMember);
        }
    }

    @Exclude
    public boolean makeAdmin(String newAdmin){
        if(membersIDs != null && membersIDs.contains(newAdmin)){
            adminsIDs.add(newAdmin);
            return true;
        }
        //Cannot add user that is not a member of the group
        return false;
    }
    @Exclude
    public boolean addFollower(String newFollower){
        if(followersIDs != null) {
            return this.followersIDs.add(newFollower);
        }else{
            followersIDs = new ArrayList<>();
            return followersIDs.add(newFollower);
        }
    }

    @Exclude
    public boolean addFollowers(List<String> newFollowers){
        if (followersIDs != null) {
            return this.followersIDs.addAll(newFollowers);
        }else{
            followersIDs = new ArrayList<>();
            return followersIDs.addAll(newFollowers);
        }
    }

    @Exclude
    public int getTotalMembers(){
        return 1 + getSizeofList(membersIDs) + getSizeofList(adminsIDs) + getSizeofList(followersIDs); //1 is for the leader
    }

    @Exclude
    public void changeLeader(String newLeaderID){
        this.leaderID = newLeaderID;
    }

    @Exclude
    public void increaseTotalPointsEarned(int newPoints){
        this.totalPointsEarned += newPoints;
    }

    @Exclude
    public void decreaseTotlaPointsEarned(int newPoints){
        if(totalPointsEarned > 0 && (totalPointsEarned - newPoints >0)){
            //Have enough points to remove
            totalPointsEarned -= newPoints;
        }else{
            //Removing points would put you below zero and zero is the min
            totalPointsEarned = 0;
        }
    }

    private int getSizeofList(List<String> list){
        if (list == null){
            return 0;
        }
        return list.size();
    }

    @Exclude
    public int getNumberOfPosts(){
        return getSizeofList(postList);
    }

    public Map<String,Object> toMap(){

        Map<String, Object> map = new HashMap<>();
        map.put("groupName",groupName);
        map.put("groupID",groupID);
        map.put("totalPointsEarned",totalPointsEarned);
        map.put("postList",postList);
        map.put("mission",mission);
        map.put("membersIDs",membersIDs);
        map.put("leaderID",leaderID);
        map.put("adminsIDs",adminsIDs);
        map.put("followersIDs",followersIDs);
        map.put("requirementForMember",requirementForMember);
        map.put("requirementForAdmin",requirementForAdmin);

        return map;
    }
}
