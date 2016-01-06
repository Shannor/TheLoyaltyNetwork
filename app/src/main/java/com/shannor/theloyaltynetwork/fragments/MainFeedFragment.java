package com.shannor.theloyaltynetwork.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.PostManager;
import com.shannor.theloyaltynetwork.views.PostAdapter;

public class MainFeedFragment extends Fragment {

    RecyclerView mRecyclerView;
    PostManager mPostManager = PostManager.getInstance();
    PostAdapter mPostAdapter;
    private OnUpdatePostListener mListener;

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
        return view;
    }

    public void updateListView(){
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUpdatePostListener) {
            mListener = (OnUpdatePostListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUpdatePostListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUpdatePostListener {
        // TODO: Update argument type and name
        void updatePostList();
    }
}

