package com.shannor.theloyaltynetwork.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.model.Post;
import com.shannor.theloyaltynetwork.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shannor on 10/24/2015.
 * The adapter for the RecyclerView for the Posts being shown
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{

    List<Post> postList;

    //Constructor
    public PostAdapter(List<Post> temp){
        this.postList = temp;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_post_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int pos) {
        holder.bindTopic(postList.get(pos));
    }

    @Override
    public int getItemCount(){
        return postList.size();
    }

}
