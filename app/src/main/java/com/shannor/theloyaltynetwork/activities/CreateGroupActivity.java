package com.shannor.theloyaltynetwork.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.GroupManager;
import com.shannor.theloyaltynetwork.model.User;

public class CreateGroupActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    EditText groupName;
    EditText groupMission;
    GroupManager groupManager = GroupManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            if (groupName.getText().toString().isEmpty() || groupMission.getText().toString().isEmpty()){
                dialog.show();
            }else {
                //TODO: Add current user functionality
                //Add new Group to the list
//                groupManager.addContent();
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
