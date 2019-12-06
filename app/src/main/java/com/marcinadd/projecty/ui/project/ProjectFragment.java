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
    private ProjectListFragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_projects, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        setRetainInstance(true);
        fab.setOnClickListener(v -> {
            AddProjectDialogFragment dialogFragment = new AddProjectDialogFragment();
            dialogFragment.show(getChildFragmentManager(), "TAG");
        });
        fragment = (ProjectListFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        return view;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        if (childFragment instanceof AddProjectDialogFragment) {
            AddProjectDialogFragment addProjectDialogFragment = (AddProjectDialogFragment) childFragment;
            addProjectDialogFragment.setListener(this);
        }
    }

    @Override
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        fragment.loadData();
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }
}
