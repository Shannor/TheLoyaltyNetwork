package com.shannor.theloyaltynetwork.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.Post;
import com.shannor.theloyaltynetwork.views.CommentListAdapter;

import org.w3c.dom.Text;

public class DetailPostActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView userNameView = (TextView)findViewById(R.id.detail_user_name);
        TextView bodyView = (TextView) findViewById(R.id.detail_body);
        ListView commentsList = (ListView)findViewById(R.id.detail_comments_list);
        final EditText replyText = (EditText)findViewById(R.id.detail_write_comment);
        Button confirmButton = (Button)findViewById(R.id.detail_send_comment);
        mSessionManager = new SessionManager(this);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String body = bundle.getString("body");
        String postId = bundle.getString("id");

        CommentListAdapter adapter = new CommentListAdapter(this,R.layout.content_detail_post,postId);
        commentsList.setAdapter(adapter);

        userNameView.setText(name);
        bodyView.setText(body);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = replyText.getText().toString();
                if(!reply.isEmpty()) {
                    //TODO: Finish the Reply Function
                    Post post = new Post(mSessionManager.getUsername(), mSessionManager.getUid(), null,reply);
                }
            }
        });

    }

}
