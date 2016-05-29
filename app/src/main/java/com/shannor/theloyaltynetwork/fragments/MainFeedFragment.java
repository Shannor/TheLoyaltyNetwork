package com.shannor.theloyaltynetwork.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.PostManager;
import com.shannor.theloyaltynetwork.views.PostAdapter;

public class MainFeedFragment extends Fragment {

    RecyclerView mRecyclerView;
    PostManager mPostManager = PostManager.getInstance();
    PostAdapter mPostAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MainFeedFragment() {
        // Required empty public constructor
    }

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
//        mPostManager.createTestContent(6);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mainFeed);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mPostAdapter = new PostAdapter(mPostManager.getContents());
        mRecyclerView.setAdapter(mPostAdapter);
        return view;
    }

    //TODO: Add method that will update when new posts arrive.


}

