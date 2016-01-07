package com.shannor.theloyaltynetwork.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.model.Group;

/**
 * Created by Shannor on 1/6/2016.
 * View Holder for the RecyclerView in the Group Tab
 * Will show the Cards for the Groups
 */
public class GroupViewHolder extends RecyclerView.ViewHolder{

    private TextView mGroupName;
    private TextView mNumberOfPosts;
    private TextView mTotalMembers;
    private TextView mGroupRating;

    public GroupViewHolder(View cardView) {
        super(cardView);
        //Init of items needed for the CardView
        mGroupName = (TextView) cardView.findViewById(R.id.group_name);
        mNumberOfPosts = (TextView) cardView.findViewById(R.id.number_posts);
        mTotalMembers = (TextView) cardView.findViewById(R.id.amount_members);
        mGroupRating = (TextView)cardView.findViewById(R.id.group_rating);
    }

    public void bindTopic(Group group){
        //Binds this specific post the the Recycler View
        mGroupName.setText(group.getName());
        mNumberOfPosts.setText((String.format("%d",group.getNumPosts())));
        mTotalMembers.setText(String.format("%d",group.getTotalMembers()));
        mGroupRating.setText(String.format("%d",group.getTotalPointsEarned()));
    }
}
