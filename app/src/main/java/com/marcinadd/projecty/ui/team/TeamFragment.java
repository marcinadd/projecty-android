package com.marcinadd.projecty.ui.team;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.model.Team;
import com.marcinadd.projecty.ui.team.dialog.TeamAddDialogFragment;


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
        fab.setOnClickListener(onFabClickListener());
        return view;
    }

    View.OnClickListener onFabClickListener() {
        return v -> {
            TeamAddDialogFragment teamAddDialogFragment = new TeamAddDialogFragment();
            teamAddDialogFragment.setListener(onTeamAddedListener());
            teamAddDialogFragment.show(getChildFragmentManager(), "TAG");
        };
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

    RetrofitListener<Team> onTeamAddedListener() {
        return new RetrofitListener<Team>() {
            @Override
            public void onResponseSuccess(Team response, @Nullable String TAG) {
                fragment.loadData();
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {

            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getChildFragmentManager().getFragments().forEach(fragment -> {
                if (fragment instanceof TeamAddDialogFragment) {
                    ((TeamAddDialogFragment) fragment).setListener(onTeamAddedListener());
                }
            });
        }
    }
}
