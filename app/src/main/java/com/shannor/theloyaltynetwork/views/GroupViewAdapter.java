package com.shannor.theloyaltynetwork.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.model.Group;
import com.shannor.theloyaltynetwork.model.Post;

import java.util.List;

/**
 * Created by Shannor on 1/6/2016.
 * Adapter for all Group View Cards needed.
 */
public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    List<Group> groupList;

    //Constructor
    public GroupViewAdapter(List<Group> groups){
        this.groupList = groups;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_post_layout, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int pos) {
        holder.bindTopic(groupList.get(pos));
    }

    @Override
    public int getItemCount(){
        return groupList.size();
    }
}
