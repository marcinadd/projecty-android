package com.marcinadd.projecty.ui.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.ui.project.dialog.AddProjectDialogFragment;

public class ProjectFragment extends Fragment implements RetrofitListener<Void>, ProjectListFragment.OnDataLoadedListener {
    private ProjectListFragment fragment;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            AddProjectDialogFragment dialogFragment = new AddProjectDialogFragment();
            dialogFragment.show(getChildFragmentManager(), "TAG");
        });
        fragment = (ProjectListFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        fragment.setOnDataLoadedListener(this);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        if (childFragment instanceof AddProjectDialogFragment) {
            AddProjectDialogFragment addProjectDialogFragment = (AddProjectDialogFragment) childFragment;
            addProjectDialogFragment.setListener(this);
        }
    }

    //Add project listeners
    @Override
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        fragment.loadData();
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        progressBar = null;
    }

    @Override
    public void onDataLoaded() {
        progressBar.setVisibility(View.GONE);
    }
}
