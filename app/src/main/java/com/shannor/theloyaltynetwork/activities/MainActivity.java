package com.shannor.theloyaltynetwork.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.User;
import com.shannor.theloyaltynetwork.views.MainActivityFragmentAdapter;


public class MainActivity extends AppCompatActivity {

    static final int CREATE_POST_REQUEST = 1;  // The request code for resultActivity
    static final int CREATE_GROUP_REQUREST = 2;

    private FloatingActionButton fab;
    private SessionManager mSessionManager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    //TODO: Add "Your Group" fragment to display all the current users Groups or Create one of their own
    //TODO: When clicking on a card open up the discussion

    //TODO:remove Entity Class

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        mSessionManager = new SessionManager(this);
        checkLogin();
        newMemberRequest();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager mViewPager = (ViewPager)findViewById(R.id.viewpager);
        TabLayout mTabLayout = (TabLayout)findViewById(R.id.fragment_tabs);

        MainActivityFragmentAdapter mainActivityFragmentAdapter = new MainActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainActivityFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        fab = (FloatingActionButton) findViewById(R.id.main_fab);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            /**
             * Method used to change out Fab buttons or remove it all together.
             * @param position which fragment currently on
             */
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        fab.show();
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initPost();
                            }
                        });
                        break;
                    case 1:
                        fab.show();
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initGroup();
                            }
                        });
                        break;
                    case 2:
                        fab.hide();
                        break;
                    default:
                        fab.hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout){
            mSessionManager.logoutUser();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to just start the create Post activity
     */
    public void initPost(){
        Intent intent = new Intent(this,CreatePostActivity.class);
        startActivityForResult(intent,CREATE_POST_REQUEST);
    }

    public void initGroup(){
        Intent intent = new Intent(this,CreateGroupActivity.class);
        startActivityForResult(intent,CREATE_GROUP_REQUREST);
    }
    /**
     * Method designed to get the result from the CreatePostActivity.
     * If OK, will update the List
     * If Canceled will do nothing
     * @param requestCode Code to make sure its the right Activity returning
     * @param resultCode Either OK or Cancel
     * @param data Information returned
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Checks from different results from activities
        if (requestCode == CREATE_POST_REQUEST) {
            //If post was created successfully
            if(resultCode == Activity.RESULT_OK){

            }else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else if (requestCode == CREATE_GROUP_REQUREST){
            if (resultCode == RESULT_OK){

            }else if(resultCode == RESULT_CANCELED){

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Method to check if this is a first time visitor.
     * Checks by looking into the database for the Uid
     */
    public void newMemberRequest(){
        final DatabaseReference userRef = database.getReference("users");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(mSessionManager.getUid())){
                    User currentUser =  dataSnapshot.child(mSessionManager.getUid()).getValue(User.class);
                    mSessionManager.setUsername(currentUser.getName());

                }else{
                    //Dialog to set add New User to the Database
                    Dialog d = inputDialog(userRef);
                    d.show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DataBase error",databaseError.toString());
            }
        });
    }

    public void checkLogin(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Signed in", "onAuthStateChanged:signed_in:" + user.getUid());
                    mSessionManager.setUid(user.getUid());
                } else {
                    // User is signed out
                    Log.d("Signed Out", "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public Dialog inputDialog(final DatabaseReference userRef){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your username.");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        input.setTextColor(Color.BLACK);
        input.setHint("TheBest742");
        builder.setView(input);
        //TODO: Check database that userName doesn't already Exist
        //TODO: Check if userName follows some rule
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userName = input.getText().toString();
                //Valid String check
                if (!userName.isEmpty()) {
                    mSessionManager.setUsername(userName);
                    User newUser = new User(userName);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        userRef.child(user.getUid()).setValue(newUser);
                    }
                }
            }
        });
        return builder.create();
    }

}
