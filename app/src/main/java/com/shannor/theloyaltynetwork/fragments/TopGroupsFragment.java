package com.shannor.theloyaltynetwork.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.GroupManager;
import com.shannor.theloyaltynetwork.views.GroupViewAdapter;


public class TopGroupsFragment extends Fragment {

    GroupManager groupManager = GroupManager.getInstance();
    RecyclerView recyclerView;
    GroupViewAdapter mAdapter;

    public TopGroupsFragment() {
        // Required empty public constructor
    }

    public static TopGroupsFragment newInstance() {
        return new TopGroupsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_top_groups, container, false);
        groupManager.createTestContent(5);
        recyclerView = (RecyclerView)view.findViewById(R.id.top_group_feed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GroupViewAdapter(groupManager.getContents());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

}
