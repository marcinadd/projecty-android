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
import com.marcinadd.projecty.listener.AddTaskListener;
import com.marcinadd.projecty.listener.TaskListResponseListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.fragment.AddTaskDialogFragment;
import com.marcinadd.projecty.task.fragment.TaskFragment;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.task.model.TaskListResponseModel;
import com.marcinadd.projecty.task.ui.main.SectionsStatePagerAdapter;

public class TaskListFragment extends Fragment implements TaskFragment.OnListFragmentInteractionListener, AddTaskListener, TaskListResponseListener {
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
        projectId = TaskListFragmentArgs.fromBundle(getArguments()).getProjectId();
        TaskService.getInstance(getContext()).getTaskList(projectId, this);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskDialogFragment addTaskDialogFragment = new AddTaskDialogFragment(projectId, TaskListFragment.this);
                addTaskDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });
        return root;
    }

    @Override
    public void onListFragmentInteraction(Task item) {
    }

    @Override
    public void onTaskAdded(Task newTask) {
        sectionsStatePagerAdapter.addTaskToDo(newTask);
    }

    @Override
    public void onAddFailed() {
    }


    @Override
    public void onTaskListResponse(TaskListResponseModel model) {
        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(
                getContext(), getChildFragmentManager(),
                model.getToDoTasks(), model.getInProgressTasks(), model.getDoneTasks(), projectId);
        viewPager.setAdapter(sectionsStatePagerAdapter);
        tabs.setupWithViewPager(viewPager);
        sectionsStatePagerAdapter.notifyDataSetChanged();
    }
}