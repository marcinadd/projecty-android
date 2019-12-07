package com.marcinadd.projecty.ui.team.manage;

import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.TeamService;
import com.marcinadd.projecty.team.model.ManageTeamResponseModel;
import com.marcinadd.projecty.team.model.Team;
import com.marcinadd.projecty.team.model.TeamRole;
import com.marcinadd.projecty.ui.team.manage.dialog.AddTeamRolesDialogFragment;
import com.marcinadd.projecty.ui.team.manage.dialog.ChangeTeamNameDialogFragment;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ManageTeamFragment extends Fragment implements ChangeTeamNameDialogFragment.OnTeamNameChangedListener, AddTeamRolesDialogFragment.OnTeamRolesAddedListener {
    private static final String TAG = "TAG";

    private ManageTeamViewModel mViewModel;

    private long teamId;
    private TextView textViewName;
    private TeamRoleFragment teamRoleFragment;

    public static ManageTeamFragment newInstance() {
        return new ManageTeamFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_manage, container, false);
        textViewName = view.findViewById(R.id.team_manage_name);
        teamId = ManageTeamFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getTeamId();
        TeamService.getInstance(getContext()).getTeamWithRoles(teamId, getManageTeamResponseModelListener());
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
            case R.id.role_add:
                addRole();
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
            if (teamRoleFragment != null) {
                teamRoleFragment.updateRolesInRecyclerViewAdapter(teamRoles);
            }
        };
    }

    private void changeName() {
        ChangeTeamNameDialogFragment changeTeamNameDialogFragment =
                ChangeTeamNameDialogFragment.newInstance(mViewModel.getTeam().getValue());
        changeTeamNameDialogFragment.setOnTeamNameChangedListener(this);
        changeTeamNameDialogFragment.show(getChildFragmentManager(), TAG);
    }

    private void addRole() {
        AddTeamRolesDialogFragment fragment =
                AddTeamRolesDialogFragment.newInstance(mViewModel.getTeam().getValue());
        fragment.setOnTeamRolesAddedListener(this);
        fragment.show(Objects.requireNonNull(getChildFragmentManager()), TAG);
    }


    private void loadProjectRoleManageFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("teamRoles", (Serializable) mViewModel.getTeamRoles().getValue());
        teamRoleFragment = new TeamRoleFragment();
        teamRoleFragment.setArguments(bundle);
        FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        transaction.replace(R.id.frameLayout3, teamRoleFragment);
        transaction.commit();
    }



    private RetrofitListener<ManageTeamResponseModel> getManageTeamResponseModelListener() {
        return new RetrofitListener<ManageTeamResponseModel>() {
            @Override
            public void onResponseSuccess(ManageTeamResponseModel response, @Nullable String TAG) {
                mViewModel.setTeam(response.getTeam());
                mViewModel.setTeamRoles(response.getTeamRoles());
                loadProjectRoleManageFragment();
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {

            }
        };
    }

    private synchronized RetrofitListener<List<TeamRole>> onTeamRolesAddListener() {
        return new RetrofitListener<List<TeamRole>>() {
            @Override
            public void onResponseSuccess(List<TeamRole> response, @Nullable String TAG) {
                List<TeamRole> value = mViewModel.getTeamRoles().getValue();
                Objects.requireNonNull(value).addAll(response);
                mViewModel.setTeamRoles(value);
                response.forEach(teamRole -> Log.e("TeamRole", teamRole.toString()));
                response.forEach(role -> teamRoleFragment.addRoleToRecyclerViewAdapater(role));
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
                if (fragment instanceof ChangeTeamNameDialogFragment) {
                    ((ChangeTeamNameDialogFragment) fragment).setOnTeamNameChangedListener(this);
                } else if (fragment instanceof AddTeamRolesDialogFragment)
                    ((AddTeamRolesDialogFragment) fragment).setOnTeamRolesAddedListener(this);
            });
        }
    }

    @Override
    public void onTeamNameChanged(String newTeamName) {
        Team team = mViewModel.getTeam().getValue();
        Objects.requireNonNull(team).setName(newTeamName);
        mViewModel.setTeam(team);
    }

    @Override
    public void onTeamRolesAdded(List<TeamRole> teamRoles) {
        List<TeamRole> roleList = mViewModel.getTeamRoles().getValue();
        Objects.requireNonNull(roleList).addAll(teamRoles);
        mViewModel.setTeamRoles(roleList);
    }
}
