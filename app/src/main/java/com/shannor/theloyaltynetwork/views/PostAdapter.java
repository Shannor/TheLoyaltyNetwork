package com.shannor.theloyaltynetwork.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.activities.DetailPostActivity;
import com.shannor.theloyaltynetwork.model.Post;
import com.shannor.theloyaltynetwork.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shannor on 10/24/2015.
 * The adapter for the RecyclerView for the Posts being shown
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{

    private List<Post> postList;
    private List<String> mKeys;
    private Context context;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private boolean disagreeSelected =false;
    private boolean agreesSelected = false;

    //Constructor
    public PostAdapter(Context context){
        this.postList = new ArrayList<>();
        this.mKeys = new ArrayList<>();
        this.context = context;
        Query postRef = database.getReference("posts").limitToFirst(100);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Child Added", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Post post = dataSnapshot.getValue(Post.class);
                String key = dataSnapshot.getKey();

                //Insert into the correct location, based on previousChildName
                //Currently putting all the post at the beginning because Firebase doesn't support
                //Descending order yet
                postList.add(0,post);
                mKeys.add(0,key);
//                if (previousChildName == null) {
//                    postList.add(0,post);
//                    mKeys.add(0, key);
//                } else {
//                    int previousIndex = mKeys.indexOf(previousChildName);
//                    int nextIndex = previousIndex + 1;
//                    //Last item
//                    if (nextIndex == postList.size()) {
//                        postList.add(0,post);
//                        mKeys.add(0,key);
//                    } else {
//                        //In the middle
//                        postList.add(0, post);
//                        mKeys.add(0, key);
//                    }
//                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Post", "onChildChanged:" + dataSnapshot.getKey());

                // A Post has changed, use the key to determine if we are displaying this
                // Post and if so displayed the changed Post.
                Post newPost = dataSnapshot.getValue(Post.class);
                String postKey = dataSnapshot.getKey();

                int index = mKeys.indexOf(postKey);
                postList.set(index, newPost);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("Post", "onChildRemoved:" + dataSnapshot.getKey());

                // A Post has changed, use the key to determine if we are displaying this
                // Post and if so remove it.
                String postKey = dataSnapshot.getKey();
                int index = mKeys.indexOf(postKey);

                mKeys.remove(index);
                postList.remove(index);
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Post", "onChildMoved:" + dataSnapshot.getKey());

                // A Post has changed position, use the key to determine if we are
                // displaying this Post and if so move it.
                Post movedPost = dataSnapshot.getValue(Post.class);
                String postKey = dataSnapshot.getKey();
                int index = mKeys.indexOf(postKey);
                postList.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    postList.add(0, movedPost);
                    mKeys.add(0, postKey);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == postList.size()) {
                        postList.add(movedPost);
                        mKeys.add(postKey);
                    } else {
                        postList.add(nextIndex, movedPost);
                        mKeys.add(nextIndex, postKey);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Post", "postPosts:onCancelled", databaseError.toException());
            }
        };
        postRef.addChildEventListener(childEventListener);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_post_layout, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, final int pos) {
        holder.bindTopic(postList.get(pos));
        final Post post = postList.get(pos);
        final Button agreeBtn = holder.getAgreeButton();
        final Button disagreeBtn = holder.getDisagreeButton();
        Button commentBtn = holder.getCommentsButton();
        final TextView pointsLabel = holder.getPointsLabel();

        final DatabaseReference postref = database.getReference("posts").child(post.getMyPostID());

        //Open comments activity
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPostActivity.class);
                intent.putExtra("id",post.getMyPostID());
                intent.putExtra("name",post.getUserName());
                intent.putExtra("body",post.getBody());
                context.startActivity(intent);
            }
        });
//        TODO: Add point logic here later for loyalty points
        disagreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Post serverSidePost = dataSnapshot.getValue(Post.class);
                        if( !getDisagreeSelectionValue() && !getAgreeSelectionValue()){
                            setDisagree(true);
                            serverSidePost.increaseDisagree();
                        }
                        //Unselect Disagree
                        else if(getDisagreeSelectionValue() && !getAgreeSelectionValue()){
                            setDisagree(false);
                            serverSidePost.decreaseDisagree();
                        }
                        //Swap Disagree and Agree selection, only one selected at a time.
                        else {
                            setAgree(false);
                            setDisagree(true);
                            serverSidePost.increaseDisagree();
                            serverSidePost.decreaseAgree();
                        }
                        postref.child("disagree").setValue(serverSidePost.getDisagree());
                        postref.child("agree").setValue(serverSidePost.getAgree());
                        pointsLabel.setText(String.format(Locale.getDefault(),"%s: %d | %s : %d","For",serverSidePost.getAgree()
                                ,"Against",serverSidePost.getDisagree()));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Error updating disagree",databaseError.toString());
                    }
                });
            }
        });

        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Post serverSidePost = dataSnapshot.getValue(Post.class);
                        //Start state, neither selected
                        if( !getDisagreeSelectionValue() && !getAgreeSelectionValue()){
                            setAgree(true);
                            serverSidePost.increaseAgree();
                        }
                        //Unselect Agree
                        else if(getAgreeSelectionValue()&& !getDisagreeSelectionValue()){
                            setAgree(false);
                            serverSidePost.decreaseAgree();
                        }
                        //Swap Disagree and Agree selection, only one selected at a time.
                        else {
                            setDisagree(false);
                            setAgree(true);
                            serverSidePost.increaseAgree();
                            serverSidePost.decreaseDisagree();
                        }
                        postref.child("agree").setValue(serverSidePost.getAgree());
                        postref.child("disagree").setValue(serverSidePost.getDisagree());
                        pointsLabel.setText(String.format(Locale.getDefault(),"%s: %d | %s : %d","For",serverSidePost.getAgree(),
                                "Against",serverSidePost.getDisagree()));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Error updating agree",databaseError.toString());

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount(){
        return postList.size();
    }

    public Post getPost(int position){
        return postList.get(position);
    }

    private void setDisagree(boolean value){
        disagreeSelected = value;
    }

    private void setAgree(boolean value){
        agreesSelected = value;
    }

    private boolean getDisagreeSelectionValue(){
        return disagreeSelected;
    }

    private boolean getAgreeSelectionValue(){
        return agreesSelected;
    }
}
