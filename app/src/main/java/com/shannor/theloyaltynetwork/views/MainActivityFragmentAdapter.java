package com.shannor.theloyaltynetwork.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shannor.theloyaltynetwork.fragments.MainFeedFragment;
import com.shannor.theloyaltynetwork.fragments.TopGroupsFragment;

/**
 * Created by Shannor on 12/24/2015.
 * Fragment adapter used for all the fragments in the mainFeed
 */
public class MainActivityFragmentAdapter extends FragmentPagerAdapter {

        String[] fragmentTitles = {
                "Main Feed",
                "Top Groups"
        };

        public MainActivityFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainFeedFragment();
                case 1:
                    return new TopGroupsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragmentTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles[position];
        }
}
