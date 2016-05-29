package com.shannor.theloyaltynetwork.mangers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.model.Post;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shannor on 12/23/2015.
 * Manger that will handle all post related methods
 * Class will be a singleton
 */
public class PostManager{

    private static PostManager instance = null ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postRef = database.getReference("posts");
    private List<Post> postList = new ArrayList<>();

    private PostManager(){}

    public static PostManager getInstance(){
        if(instance == null){
            instance = new PostManager();
        }
        return instance;
    }

    public void addPost(Post newPost){

    }


}
