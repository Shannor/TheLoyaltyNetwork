package com.shannor.theloyaltynetwork.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.GroupManager;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.Group;
import com.shannor.theloyaltynetwork.views.GroupViewAdapter;


public class GroupFragment extends Fragment {

    private GroupManager groupManager = GroupManager.getInstance();
    private static final String ARG_PARAM1 = "layoutID";
    private static final String ARG_PARAM2 = "recyclerViewID";
    private static final String ARG_PARAM3 = "groupFragmentType";
    //Variables to hold LayoutID and RecyclerView ID
    private int mLayoutID;
    private int mRecyclerViewID;
    private String mFragmentType;
    private SessionManager mSessionManager;
    public GroupFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param layoutID Layout Resource ID.
     * @param recyclerViewID Recycler View ID.
     * @return A new instance of fragment BlankFragment.
     */
    public static GroupFragment newInstance(int layoutID, int recyclerViewID, String fragmentType) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, layoutID);
        args.putInt(ARG_PARAM2, recyclerViewID);
        args.putString(ARG_PARAM3,fragmentType);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLayoutID = getArguments().getInt(ARG_PARAM1);
            mRecyclerViewID = getArguments().getInt(ARG_PARAM2);
            mFragmentType = getArguments().getString(ARG_PARAM3);
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
//        If statement based on the Layout being worked on\
//        TODO:Test out viewing groups may not work because using the same fragment!
        GroupViewAdapter mAdapter = null;
        if (mFragmentType.equals("TopGroups")){
            mAdapter = new GroupViewAdapter(database.getReference("groups"));

        }else if (mFragmentType.equals("MyGroups")){
            mAdapter = new GroupViewAdapter(database.getReference("users").child(mSessionManager.getUid()).child("affiliations"));

        }
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
