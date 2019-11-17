package com.marcinadd.projecty.ui.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.project.AddProjectDialogFragment;

public class ProjectFragment extends Fragment implements RetrofitListener<Void> {
    private View view;
    private FloatingActionButton fab;
    private ProjectListFragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_projects, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProjectDialogFragment dialogFragment = new AddProjectDialogFragment(ProjectFragment.this);
                dialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });
        fragment = (ProjectListFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        return view;
    }


    @Override
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        fragment.loadData();
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }
}
