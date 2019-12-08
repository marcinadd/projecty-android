package com.marcinadd.projecty.ui.team.project;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.team.TeamService;
import com.marcinadd.projecty.team.model.TeamProjectListResponseModel;


public class TeamProjectFragment extends Fragment implements RetrofitListener<TeamProjectListResponseModel> {

    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private long teamId;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            teamId = TeamProjectFragmentArgs.fromBundle(getArguments()).getTeamId();
        }
        view = inflater.inflate(R.layout.fragment_team_project_list, container, false);
        TeamService.getInstance(getContext()).getProjects(teamId, this);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        view = null;
    }

    @Override
    public void onResponseSuccess(TeamProjectListResponseModel response, @Nullable String TAG) {
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new MyTeamProjectRecyclerViewAdapter(response.getProjects(), mListener));
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Project item);
    }
}
