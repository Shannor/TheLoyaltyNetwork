package com.shannor.theloyaltynetwork.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.Post;
import com.shannor.theloyaltynetwork.model.User;

import java.util.ArrayList;

public class CreatePostActivity extends AppCompatActivity {

    private EditText title;
    private EditText body;
    private AlertDialog.Builder builder;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSessionManager = new SessionManager(this);

        title = (EditText) findViewById(R.id.post_title_view);
        body = (EditText)findViewById(R.id.post_body_view);

        builder = new AlertDialog.Builder(this);
        title.requestFocus();
        showKeyBoard();

        builder.setMessage(R.string.empty_text)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Just a notification that nothing is filled out
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_post) {
            //Set Alert Box to show information
            AlertDialog dialog = builder.create();
            //Error check to make sure nothing is empty
            String title2 = title.getText().toString();
            String body2 = body.getText().toString();
            if (title2.isEmpty() || body2.isEmpty()){
                dialog.show();
            }else {
                //Just show a substring of the Body text
                final Post post = new Post(mSessionManager.getUsername(),mSessionManager.getUid(),title2,body2);
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference postRef = database.getReference("posts");
                final DatabaseReference userRef = database.getReference("users");


                userRef.child(mSessionManager.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String key = postRef.push().getKey();

                        User user = dataSnapshot.getValue(User.class);
                        if (user.getPostList() == null){
                            user.setPostList(new ArrayList<String>());
                            user.getPostList().add(key);
                        }else{
                            //Adds the Recent post in front
                            user.getPostList().add(key);
                        }
                        userRef.child(mSessionManager.getUid()).updateChildren(user.toMap());
                        post.setMyPostID(key);
                        postRef.child(key).setValue(post);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Post Error",databaseError.toString());
                    }
                });

                setResult(Activity.RESULT_OK);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void showKeyBoard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
