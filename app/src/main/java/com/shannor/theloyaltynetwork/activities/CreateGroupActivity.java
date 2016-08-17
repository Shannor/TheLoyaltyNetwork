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
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.Group;
import com.shannor.theloyaltynetwork.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private EditText groupName;
    private EditText groupMission;
    private SessionManager mSessionManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSessionManager = new SessionManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupName = (EditText)findViewById(R.id.group_name_text);
        groupMission = (EditText)findViewById(R.id.group_mission_text);
        builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.empty_group_text)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Just a notification that nothing is filled out
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference groupRef = database.getReference("groups");
        final DatabaseReference userRef = database.getReference("user");
        int id = item.getItemId();

        if (id == R.id.action_create) {
            //Set Alert Box to show information
            AlertDialog dialog = builder.create();
            final String name = groupName.getText().toString();
            final String mission = groupMission.getText().toString();
            //Error check to make sure nothing is empty
            if (name.isEmpty() || mission.isEmpty()){
                dialog.show();
            }else {
                userRef.child(mSessionManager.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        String key = groupRef.push().getKey();
                        Group group = new Group(mSessionManager.getUid(), name, mission);
                        group.setGroupID(key);
                        //If first group they are apart of
                        if(user.getAffiliations().isEmpty()){
                            user.setAffiliations(new ArrayList<String>());
                        }else{
                            user.addAffiliation(name);
                        }
                        //Updates Atomically
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/groups/" + key, group.toMap());
                        childUpdates.put("/users/" + mSessionManager.getUid() + "/" , user.toMap());
                        database.getReference().updateChildren(childUpdates);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Group Creation Error",databaseError.toString());
                    }
                });
                setResult(Activity.RESULT_OK);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
