package com.shannor.theloyaltynetwork.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.views.GroupViewAdapter;


public class TopGroupFragment extends Fragment {

    private static final String ARG_PARAM1 = "layoutID";
    private static final String ARG_PARAM2 = "recyclerViewID";
    //Variables to hold LayoutID and RecyclerView ID
    private int mLayoutID;
    private int mRecyclerViewID;
    private SessionManager mSessionManager;
    public TopGroupFragment() {
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
    public static TopGroupFragment newInstance(int layoutID, int recyclerViewID) {
        TopGroupFragment fragment = new TopGroupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, layoutID);
        args.putInt(ARG_PARAM2, recyclerViewID);
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
        GroupViewAdapter mAdapter = new GroupViewAdapter(FirebaseDatabase.getInstance().getReference("groups"));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
