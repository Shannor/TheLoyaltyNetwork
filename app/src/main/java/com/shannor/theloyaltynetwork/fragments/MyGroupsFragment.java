package com.shannor.theloyaltynetwork.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.views.GroupViewAdapter;

public class MyGroupsFragment extends Fragment {
    private static final String ARG_PARAM1 = "layoutID";
    private static final String ARG_PARAM2 = "RecyclerViewID";

    private int mLayoutID;
    private int mRecyclerViewID;
    private SessionManager mSessionManager;

    public MyGroupsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param layoutID Parameter 1.
     * @param recViewID Parameter 2.
     * @return A new instance of fragment MyGroupsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyGroupsFragment newInstance(int layoutID, int recViewID) {
        MyGroupsFragment fragment = new MyGroupsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, layoutID);
        args.putInt(ARG_PARAM2, recViewID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLayoutID = getArguments().getInt(ARG_PARAM1);
            mRecyclerViewID = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(mLayoutID, container, false);

        mSessionManager = new SessionManager(getContext());
        RecyclerView recyclerView = (RecyclerView)view.findViewById(mRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        GroupViewAdapter mAdapter = new GroupViewAdapter(database.getReference("users")
//                .child(mSessionManager.getUid()).child("affiliations"));
//        recyclerView.setAdapter(mAdapter);

        return view;
    }


}
