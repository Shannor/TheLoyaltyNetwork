package com.shannor.theloyaltynetwork.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.model.Group;
import com.shannor.theloyaltynetwork.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shannor on 1/6/2016.
 * Adapter for all Group View Cards needed.
 */
public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    List<Group> groupList;
    private List<String> mKeys;
    //Constructor
    public GroupViewAdapter(DatabaseReference reference){

        groupList = new ArrayList<>();
        mKeys = new ArrayList<>();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Child Added", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Group post = dataSnapshot.getValue(Group.class);
                String key = dataSnapshot.getKey();


                if (previousChildName == null) {
                    groupList.add(0,post);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    //Last item
                    if (nextIndex == groupList.size()) {
                        groupList.add(post);
                        mKeys.add(key);
                    } else {
                        //In the middle
                        groupList.add(previousIndex, post);
                        mKeys.add(previousIndex, key);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Post", "onChildChanged:" + dataSnapshot.getKey());

                // A Post has changed, use the key to determine if we are displaying this
                // Post and if so displayed the changed Post.
                Group newGroup = dataSnapshot.getValue(Group.class);
                String postKey = dataSnapshot.getKey();

                int index = mKeys.indexOf(postKey);
                groupList.set(index, newGroup);
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
                groupList.remove(index);
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Post", "onChildMoved:" + dataSnapshot.getKey());

                // A Post has changed position, use the key to determine if we are
                // displaying this Post and if so move it.
                Group movedGroup = dataSnapshot.getValue(Group.class);
                String postKey = dataSnapshot.getKey();
                int index = mKeys.indexOf(postKey);
                groupList.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    groupList.add(0, movedGroup);
                    mKeys.add(0, postKey);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex ==groupList.size()) {
                        groupList.add(movedGroup);
                        mKeys.add(postKey);
                    } else {
                        groupList.add(nextIndex, movedGroup);
                        mKeys.add(nextIndex, postKey);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Group", "Group:onCancelled", databaseError.toException());
            }
        };
        reference.addChildEventListener(childEventListener);
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
