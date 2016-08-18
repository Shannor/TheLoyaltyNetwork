package com.shannor.theloyaltynetwork.views;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.model.Group;
import com.shannor.theloyaltynetwork.model.Post;
import com.shannor.theloyaltynetwork.model.User;

/**
 * Created by Shannor on 10/24/2015.
 * Post View Holder to be used with the RecyclerView
 * Template for the post display to be used in the feeds
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    private TextView mNameText;
    private TextView mSubject;
    private TextView mTime;
    private TextView mBody;
    private TextView mPoints;
    private Button mAgreeBtn;
    private Button mDisagreeBtn;
    private Button mCommentsBtn;

    public PostViewHolder(View cardView){
        super(cardView);
        //Init of items needed for the CardView
        mNameText = (TextView)cardView.findViewById(R.id.user_name);
        mSubject = (TextView)cardView.findViewById(R.id.subject_title);
        mTime = (TextView)cardView.findViewById(R.id.time_text);
        mBody = (TextView)cardView.findViewById(R.id.subject_body);
        mPoints = (TextView)cardView.findViewById(R.id.post_points);
        mAgreeBtn = (Button)cardView.findViewById(R.id.agree_btn);
        mDisagreeBtn = (Button)cardView.findViewById(R.id.disagree_btn);
        mCommentsBtn = (Button)cardView.findViewById(R.id.comments_btn);
    }

    public void bindTopic(Post post){
        //Binds this specific post the the Recycler View
        mNameText.setText(post.getUserName());
        mSubject.setText(post.getTitle());
        mTime.setText(post.getTime());
        mBody.setText(post.getBody());
//        mPosts.setText(String.format("%s: %d | %s : %d","For",post.getAgree(),"Against",post.getDisagree()));
    }

    public Button getAgreeButton(){
        return mAgreeBtn;
    }

    public Button getCommentsButton(){
        return mCommentsBtn;
    }

    public Button getDisagreeButton(){
        return mDisagreeBtn;
    }

    public TextView getPointsLabel(){
        return mPoints;
    }
}
