package com.marcinadd.projecty.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.dialog.AddTaskDialogFragment;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.task.model.TaskListResponseModel;

import java.util.Objects;

public class TaskListFragment extends Fragment implements TaskFragment.OnListFragmentInteractionListener {
    private long projectId;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);
        viewPager = root.findViewById(R.id.view_pager);
        tabs = root.findViewById(R.id.tabs);
        projectId = TaskListFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getProjectId();
        progressBar = root.findViewById(R.id.progress_bar);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("projectId", projectId);
                AddTaskDialogFragment addTaskDialogFragment = new AddTaskDialogFragment();
                addTaskDialogFragment.setArguments(bundle);
                addTaskDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });
        return root;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        if (childFragment instanceof AddTaskDialogFragment) {
            AddTaskDialogFragment fragment = (AddTaskDialogFragment) childFragment;
            fragment.setAddTaskListener(taskAddRetrofitListener());
        } else if (childFragment instanceof TaskFragment) {
            TaskFragment taskFragment = (TaskFragment) childFragment;
            taskFragment.setTaskStatusChangedListener((task, newTaskStatus) -> {
                sectionsStatePagerAdapter.onTaskStatusChanged(task, newTaskStatus);
            });
        }
    }

    @Override
    public void onListFragmentInteraction(Task item) {
    }


    private RetrofitListener<TaskListResponseModel> taskListResponseModelRetrofitListener() {
        return new RetrofitListener<TaskListResponseModel>() {
            @Override
            public void onResponseSuccess(TaskListResponseModel response, @Nullable String TAG) {
                sectionsStatePagerAdapter = new SectionsStatePagerAdapter(
                        getContext(), getChildFragmentManager(),
                        response.getToDoTasks(), response.getInProgressTasks(), response.getDoneTasks(), projectId);
                viewPager.setAdapter(sectionsStatePagerAdapter);
                tabs.setupWithViewPager(viewPager);
                sectionsStatePagerAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {

            }
        };
    }

    private RetrofitListener<Task> taskAddRetrofitListener() {
        return new RetrofitListener<Task>() {
            @Override
            public void onResponseSuccess(Task response, @Nullable String TAG) {
                sectionsStatePagerAdapter.addTaskToDo(response);
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {

            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        TaskService.getInstance(getContext()).getTaskList(projectId, taskListResponseModelRetrofitListener());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        progressBar = null;
        viewPager = null;
    }
}