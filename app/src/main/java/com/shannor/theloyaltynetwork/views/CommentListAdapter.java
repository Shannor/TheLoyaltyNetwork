package com.shannor.theloyaltynetwork.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shannortrotty on 5/29/16.
 */
public class CommentListAdapter extends ArrayAdapter<Post> {

    private List<Post> commentsList;
    private List<String> mKeys;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public CommentListAdapter(Context context, int textViewResourceId, String postID) {
        super(context, textViewResourceId);
        commentsList = new ArrayList<>();
        mKeys = new ArrayList<>();
        DatabaseReference postRef = database.getReference("posts").child(postID).child("replies");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Child Added", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Post post = dataSnapshot.getValue(Post.class);
                String key = dataSnapshot.getKey();


                if (previousChildName == null) {
                    commentsList.add(0,post);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    //Last item
                    if (nextIndex == commentsList.size()) {
                        commentsList.add(post);
                        mKeys.add(key);
                    } else {
                        //In the middle
                        commentsList.add(previousIndex, post);
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
                Post newPost = dataSnapshot.getValue(Post.class);
                String postKey = dataSnapshot.getKey();

                int index = mKeys.indexOf(postKey);
                commentsList.set(index, newPost);
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
                commentsList.remove(index);
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
                commentsList.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    commentsList.add(0, movedPost);
                    mKeys.add(0, postKey);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == commentsList.size()) {
                        commentsList.add(movedPost);
                        mKeys.add(postKey);
                    } else {
                        commentsList.add(nextIndex, movedPost);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());

            v = vi.inflate(R.layout.comment_post_layout, null);
        }

        Post p = getItem(position);

        if (p != null) {
            TextView userName = (TextView) v.findViewById(R.id.reply_user_name);
            TextView response = (TextView) v.findViewById(R.id.reply_response);
            TextView timeStamp = (TextView) v.findViewById(R.id.reply_time_stamp);

            if (userName != null) {
                userName.setText(p.getUserName());
            }

            if (response != null) {
                response.setText(p.getBody());
            }

            if (timeStamp != null) {
                timeStamp.setText(p.getTime());
            }
        }

        return v;
    }

    @Override
    public int getCount() {
        return commentsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Post getItem(int position) {
        return commentsList.get(position);
    }
}
