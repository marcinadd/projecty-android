package com.marcinadd.projecty.ui.project;

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
import com.marcinadd.projecty.project.ProjectService;
import com.marcinadd.projecty.project.model.ProjectRole;
import com.marcinadd.projecty.project.model.UserProject;

public class ProjectListFragment extends Fragment implements RetrofitListener<UserProject> {
    private OnListFragmentInteractionListener mListener;
    private View view;
    private OnDataLoadedListener onDataLoadedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_list, container, false);
        loadData();
        return view;
    }

    void loadData() {
        ProjectService.getInstance(getContext()).getProjects(this);
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
        view = null;
    }

    @Override
    public void onResponseSuccess(UserProject response, @Nullable String TAG) {
        //projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyProjectRecyclerViewAdapter(response.getProjectRoles(), mListener));
            onDataLoadedListener.onDataLoaded();
        }
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ProjectRole item);
    }

    public void setOnDataLoadedListener(OnDataLoadedListener onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDataLoadedListener = null;
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

}
