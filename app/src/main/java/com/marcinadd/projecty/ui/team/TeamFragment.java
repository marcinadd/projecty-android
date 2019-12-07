package com.marcinadd.projecty.ui.team;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marcinadd.projecty.R;


public class TeamFragment extends Fragment implements TeamListFragment.OnDataLoadedListener {
    private TeamListFragment fragment;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fragment = (TeamListFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        fragment.setOnDataLoadedListener(this);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }


    @Override
    public void onDataLoaded() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragment = null;
        progressBar.setVisibility(View.GONE);
    }
}
