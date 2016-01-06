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
import com.shannor.theloyaltynetwork.mangers.PostManager;
import com.shannor.theloyaltynetwork.views.MainActivityFragmentAdapter;

public class MainActivity extends AppCompatActivity implements MainFeedFragment.OnUpdatePostListener{

    ViewPager mViewPager;
    TabLayout mTabLayout;
    static final int CREATE_POST_REQUEST = 1;  // The request code
    MainActivityFragmentAdapter mainActivityFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mTabLayout = (TabLayout)findViewById(R.id.fragment_tabs);

        mainActivityFragmentAdapter = new MainActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainActivityFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPost();
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

        return super.onOptionsItemSelected(item);
    }

    public void initPost(){
        //TODO: May need to be Activity for result,also pass Current user
        Intent intent = new Intent(this,CreatePostActivity.class);
        startActivityForResult(intent,CREATE_POST_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CREATE_POST_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                updatePostList();
            }
//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
        }
    }//onActivityResult

    public void updatePostList(){
        MainFeedFragment mainFeedFragment = (MainFeedFragment) mainActivityFragmentAdapter.getmCurrentFragment();
        mainFeedFragment.updateListView();
    }
}
