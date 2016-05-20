package com.shannor.theloyaltynetwork.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.User;
import com.shannor.theloyaltynetwork.views.MainActivityFragmentAdapter;


public class MainActivity extends AppCompatActivity {

    static final int CREATE_POST_REQUEST = 1;  // The request code for resultActivity

    private FloatingActionButton fab;
    private SessionManager mSessionManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;

    //TODO: Add "Your Group" fragment to display all the current users Groups or Create one of their own
    //TODO: When clicking on a card open up the discussion

    //TODO:remove Entity Class

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Signed in", "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d("Signed Out", "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        //TODO: See if User is in the user tree first

        DatabaseReference userRef = database.getReference("users");
        User temp = new User("Shannor");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            userRef.child(user.getUid()).setValue(temp);
            //TODO: Ask user for their display name
        }


        mSessionManager = new SessionManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager mViewPager = (ViewPager)findViewById(R.id.viewpager);
        TabLayout mTabLayout = (TabLayout)findViewById(R.id.fragment_tabs);

        MainActivityFragmentAdapter mainActivityFragmentAdapter = new MainActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainActivityFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPost();
            }
        });

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
                if(position == 0){
                    fab.show();
                }else{
                    fab.hide();
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

        if (requestCode == CREATE_POST_REQUEST) {
            if(resultCode == Activity.RESULT_OK){

            }
//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
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
}
