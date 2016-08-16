package com.shannor.theloyaltynetwork.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shannor.theloyaltynetwork.R;
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

        Fragment mCurrentFragment = null;

        public MainActivityFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainFeedFragment();
                case 1:
                    return TopGroupsFragment.newInstance(R.layout.fragment_top_groups, R.id.top_group_feed);
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

        /**
         * Method used to provide the current Fragment being used.
         */
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (mCurrentFragment != object) {
                mCurrentFragment = (Fragment) object;
            }
            super.setPrimaryItem(container, position, object);
        }

        public Fragment getmCurrentFragment(){
            return mCurrentFragment;
        }
}
