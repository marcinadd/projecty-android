package com.marcinadd.projecty.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.fragment.AddTaskDialogFragment;
import com.marcinadd.projecty.task.fragment.TaskFragment;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.task.model.TaskListResponseModel;
import com.marcinadd.projecty.task.ui.main.SectionsStatePagerAdapter;

import java.util.Objects;

public class TaskListFragment extends Fragment implements TaskFragment.OnListFragmentInteractionListener {
    private long projectId;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_task_list, container, false);
        viewPager = root.findViewById(R.id.view_pager);
        tabs = root.findViewById(R.id.tabs);
        projectId = TaskListFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getProjectId();
        TaskService.getInstance(getContext()).getTaskList(projectId, taskListResponseModelRetrofitListener());
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskDialogFragment addTaskDialogFragment = new AddTaskDialogFragment(projectId, taskRetrofitListener());
                addTaskDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });
        return root;
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
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {

            }
        };
    }

    private RetrofitListener<Task> taskRetrofitListener() {
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
}