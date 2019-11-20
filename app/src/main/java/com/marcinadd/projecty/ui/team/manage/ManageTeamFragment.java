package com.marcinadd.projecty.ui.team.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.NameChangedListener;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.TeamService;
import com.marcinadd.projecty.team.model.ManageTeamResponseModel;
import com.marcinadd.projecty.team.model.Team;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.List;
import java.util.Objects;

public class ManageTeamFragment extends Fragment implements RetrofitListener<ManageTeamResponseModel>, NameChangedListener {

    private ManageTeamViewModel mViewModel;

    private long teamId;
    private TextView textViewName;

    public static ManageTeamFragment newInstance() {
        return new ManageTeamFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_team, container, false);
        textViewName = view.findViewById(R.id.team_manage_name);
        teamId = ManageTeamFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getTeamId();
        TeamService.getInstance(getContext()).getTeamWithRoles(teamId, this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_team_manage, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.name:
                changeName();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ManageTeamViewModel.class);
        mViewModel.getTeam().observe(this, teamObserver());
        mViewModel.getTeamRoles().observe(this, teamRolesObserver());
    }

    private Observer<Team> teamObserver() {
        return team -> {
            textViewName.setText(team.getName());
        };
    }

    private Observer<List<TeamRole>> teamRolesObserver() {
        return teamRoles -> {
            // TODO updae teamroles
        };
    }

    @Override
    public void onResponseSuccess(ManageTeamResponseModel response, @Nullable String TAG) {
        mViewModel.setTeam(response.getTeam());
        mViewModel.setTeamRoles(response.getTeamRoles());
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    private void changeName() {
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("team", mViewModel.getTeam().getValue());
        ChangeTeamNameDialogFragment fragment = new ChangeTeamNameDialogFragment(this);
        fragment.setArguments(bundle1);
        fragment.show(Objects.requireNonNull(getFragmentManager()), "TAG");
    }

    @Override
    public void onNameChanged(String newName) {
        Team team = mViewModel.getTeam().getValue();
        if (team != null) {
            team.setName(newName);
        }
        mViewModel.setTeam(team);
    }

}
