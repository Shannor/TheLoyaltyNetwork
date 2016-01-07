package com.shannor.theloyaltynetwork.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.fragments.MainFeedFragment;
import com.shannor.theloyaltynetwork.mangers.BusBase;
import com.shannor.theloyaltynetwork.mangers.PostManager;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.views.MainActivityFragmentAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class MainActivity extends AppCompatActivity {

    static final int CREATE_POST_REQUEST = 1;  // The request code for resultActivity
    ViewPager mViewPager;
    TabLayout mTabLayout;
    MainActivityFragmentAdapter mainActivityFragmentAdapter;
    FloatingActionButton fab;
    Bus bus; //Third party to interact with fragments
    SessionManager mSessionManager;

    //TODO: Add "Your Group" fragment to display all the current users Groups or Create one of their own
    //TODO: Redesign all the fragment View Cards to fit the information better
    //TODO: When clicking on a card open up the discussion

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        //First check if User has signed in
        mSessionManager = new SessionManager(this);
        //If they are logged in Continue
        mSessionManager.checkLogin();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mTabLayout = (TabLayout)findViewById(R.id.fragment_tabs);

        mainActivityFragmentAdapter = new MainActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainActivityFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        bus = BusBase.getInstance();

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
                bus.post("s");
            }
//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
        }
    }
}
