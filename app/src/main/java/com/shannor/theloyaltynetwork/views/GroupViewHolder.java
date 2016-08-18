package com.shannor.theloyaltynetwork.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.model.Group;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * Created by Shannor on 1/6/2016.
 * View Holder for the RecyclerView in the Group Tab
 * Will show the Cards for the Groups
 */
public class GroupViewHolder extends RecyclerView.ViewHolder{

    private TextView mGroupName;
    private TextView mNumberOfPosts;
    private TextView mTotalMembers;
    private TextView mLeader;
    private TextView mSuppportTags; //Will be a list
    private TextView mAgainstTags; //Will be a list
    private TextView mGroupMission;
    private TextView mTotalPoints;

    String[] textTitles = {
            "Post Count:",
            "Member Count:",
            "Points:"
    };

    public GroupViewHolder(View cardView) {
        super(cardView);
        //Init of items needed for the CardView
        mGroupName = (TextView) cardView.findViewById(R.id.group_name_label);
        mNumberOfPosts = (TextView) cardView.findViewById(R.id.number_posts_label);
        mTotalMembers = (TextView) cardView.findViewById(R.id.amount_members_label);
        mLeader = (TextView)cardView.findViewById(R.id.leader_label);
        mSuppportTags = (TextView)cardView.findViewById(R.id.group_support_label);
        mAgainstTags = (TextView)cardView.findViewById(R.id.group_against_label);
        mGroupMission = (TextView)cardView.findViewById(R.id.group_mission_label);
        mTotalPoints = (TextView)cardView.findViewById(R.id.group_total_points);
    }

    public void bindTopic(Group group){
        //Binds this specific post the the Recycler View
        mGroupName.setText(group.getGroupName());
        mGroupMission.setText(group.getMission());
        mNumberOfPosts.setText((String.format(Locale.getDefault(),"%s%d",textTitles[0],group.getNumberOfPosts())));
        mTotalMembers.setText(String.format(Locale.getDefault(),"%s%d",textTitles[1],group.getTotalMembers()));
        mTotalPoints.setText(String.format(Locale.getDefault(),"%s%d",textTitles[2], group.getTotalPointsEarned()));
    }
}
