package com.shannor.theloyaltynetwork.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.Post;
import com.shannor.theloyaltynetwork.model.User;
import com.shannor.theloyaltynetwork.views.CommentListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CommentPostActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SessionManager mSessionManager;
    private Button confirmButton;
    private TextView userNameView;
    private TextView bodyView;
    private ListView commentsList;
    private EditText replyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userNameView = (TextView)findViewById(R.id.detail_user_name);
        bodyView = (TextView) findViewById(R.id.detail_body);
        commentsList = (ListView)findViewById(R.id.detail_comments_list);
        replyText = (EditText)findViewById(R.id.detail_write_comment);
        confirmButton = (Button)findViewById(R.id.detail_send_comment);
        mSessionManager = new SessionManager(this);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String body = bundle.getString("body");
        String postId = bundle.getString("id");

        final DatabaseReference replyRef = database.getReference("posts").child(postId);


        CommentListAdapter adapter = new CommentListAdapter(this,R.layout.activity_detail_post,postId);
        commentsList.setAdapter(adapter);

        userNameView.setText(name);
        bodyView.setText(body);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = replyText.getText().toString();
                if(!message.isEmpty()) {
                    //null because there is no title
                    final Post reply = new Post(mSessionManager.getUsername(), mSessionManager.getUid(), null,message);

                    replyRef.child("replies").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            final String key = replyRef.push().getKey();

                            List<Post> replyList = (List<Post>)dataSnapshot.getValue();
                            if ( replyList == null){
                                replyList = new ArrayList<>();
                                replyList.add(reply);
                            }else{
//                                //Adds the Recent post in front
                                replyList.add(reply);
                            }
                            replyRef.child("replies").setValue(replyList);
                            replyText.setText("");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("Reply Error",databaseError.toString());
                        }
                    });

                }
            }
        });

    }

}
