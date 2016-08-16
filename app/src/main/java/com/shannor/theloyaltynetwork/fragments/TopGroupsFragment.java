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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "layoutID";
    private static final String ARG_PARAM2 = "recyclerViewID";

    // TODO: Rename and change types of parameters
    private int mLayoutID;
    private int mRecyclerViewID;

    public TopGroupsFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param layoutID Parameter 1.
     * @param recyclerViewID Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    public static TopGroupsFragment newInstance(int layoutID, int recyclerViewID) {
        TopGroupsFragment fragment = new TopGroupsFragment();
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
        groupManager.createTestContent(5);
        recyclerView = (RecyclerView)view.findViewById(mRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GroupViewAdapter(groupManager.getContents());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

}
