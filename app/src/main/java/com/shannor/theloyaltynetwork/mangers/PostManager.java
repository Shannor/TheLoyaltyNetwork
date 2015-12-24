package com.shannor.theloyaltynetwork.mangers;

import com.shannor.theloyaltynetwork.model.Post;
import com.shannor.theloyaltynetwork.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shannor on 12/23/2015.
 * Manger that will handle all post related methods
 * Class will be a singleton
 */
public class PostManager {

    private static PostManager instance = null ;
    private List<Post> postList = new ArrayList<>();

    private PostManager(){}

    public static PostManager getInstance(){
        if(instance == null){
            instance = new PostManager();
        }
        return instance;
    }

    public void addPost(User user, String title, String body){
        Post post = new Post(user,title,body);
        postList.add(post);
    }
    public List<Post> getPostList(){
        return postList;
    }

}
