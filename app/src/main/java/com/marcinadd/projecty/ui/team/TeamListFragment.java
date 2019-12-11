package com.marcinadd.projecty.ui.team;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.TeamService;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.List;

public class TeamListFragment extends Fragment implements RetrofitListener<List<TeamRole>> {
    private OnListFragmentInteractionListener mListener;
    private OnDataLoadedListener onDataLoadedListener;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_list, container, false);
        loadData();
        return view;
    }

    public void loadData() {
        TeamService.getInstance(getContext()).getTeams(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResponseSuccess(List<TeamRole> response, @Nullable String TAG) {
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyTeamRecyclerViewAdapter(response, mListener));
            onDataLoadedListener.onDataLoaded();
        }
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(TeamRole item);
    }

    public void setOnDataLoadedListener(OnDataLoadedListener onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }
}
