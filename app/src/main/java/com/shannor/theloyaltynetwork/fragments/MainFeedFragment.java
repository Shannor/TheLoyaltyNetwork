package com.shannor.theloyaltynetwork.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.BusBase;
import com.shannor.theloyaltynetwork.mangers.PostManager;
import com.shannor.theloyaltynetwork.views.PostAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MainFeedFragment extends Fragment {

    RecyclerView mRecyclerView;
    PostManager mPostManager = PostManager.getInstance();
    PostAdapter mPostAdapter;
    Bus bus; //Third party software to use to link information

    public MainFeedFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFeedFragment newInstance() {
        return new MainFeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main_feed, container, false);
        mPostManager.createTestContent(6);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mainFeed);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPostAdapter = new PostAdapter(mPostManager.getContents());
        mRecyclerView.setAdapter(mPostAdapter);
        bus = BusBase.getInstance();
        bus.register(this);
        return view;
    }

    /**
     * Method for the Bus that will be updated in the future.
     * Used to update the Post List when new post is added.
     * @param s temp for now, will change.
     */
    @Subscribe
    public void updatePostView(String s){
        if (s.equals("s")){
            mPostAdapter.notifyDataSetChanged();
        }
    }
}

