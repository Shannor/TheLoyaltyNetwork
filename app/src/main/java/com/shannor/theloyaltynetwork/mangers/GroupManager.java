package com.shannor.theloyaltynetwork.mangers;

import com.shannor.theloyaltynetwork.model.Group;
import com.shannor.theloyaltynetwork.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shannor on 1/3/2016.
 * Manager created to handle all group interactions.
 * Will have server interaction as well.
 */
public class GroupManager {
    private static GroupManager instance = null ;
    private List<Group> groupList = new ArrayList<>();

    private GroupManager(){}

    public static GroupManager getInstance(){
        if(instance == null){
            instance = new GroupManager();
        }
        return instance;
    }
    public void addContent(User user, String name, String mission){
        Group group = new Group(user,name,mission);
        //TODO: When server is added make sure it adds to front
        //Add to front
        groupList.add(0, group);
    }
    public void createTestContent(int amount){
        if (groupList.isEmpty()){
            for(int i = 0; i < amount; i++) {
                addContent(new User("Test" + i), "Name" + i, "Mission!" + i);
            }
        }
    }

    public List<Group> getGroupList(){
        return groupList;
    }
}
