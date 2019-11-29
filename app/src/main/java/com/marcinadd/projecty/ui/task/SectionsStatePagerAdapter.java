package com.marcinadd.projecty.ui.task;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.TaskStatusChangedListener;
import com.marcinadd.projecty.task.TaskStatus;
import com.marcinadd.projecty.task.model.Task;

import java.io.Serializable;
import java.util.List;


public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter implements TaskStatusChangedListener {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_to_do, R.string.tab_in_progress, R.string.tab_done};
    private final Context mContext;
    private List<Task> toDoTasks;
    private List<Task> inProgressTasks;
    private List<Task> doneTasks;
    private long projectId;

    private TaskFragment toDoTaskFragment;
    private TaskFragment inProgressTaskFragment;
    private TaskFragment doneTaskFragment;


    public SectionsStatePagerAdapter(Context context, FragmentManager fm,
                                     List<Task> toDoTasks,
                                     List<Task> inProgressTasks,
                                     List<Task> doneTasks,
                                     long projectId
    ) {
        super(fm);
        mContext = context;
        this.toDoTasks = toDoTasks;
        this.inProgressTasks = inProgressTasks;
        this.doneTasks = doneTasks;
        this.projectId = projectId;

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case 0:
                toDoTaskFragment = (TaskFragment) createdFragment;
                break;
            case 1:
                inProgressTaskFragment = (TaskFragment) createdFragment;
                break;
            case 2:
                doneTaskFragment = (TaskFragment) createdFragment;
                break;

        }
        return createdFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TaskFragment f1 = new TaskFragment();
                setTaskFragmentArguments(f1, toDoTasks);
                toDoTaskFragment = f1;
                return f1;
            case 1:
                TaskFragment f2 = new TaskFragment();
                setTaskFragmentArguments(f2, inProgressTasks);
                inProgressTaskFragment = f2;
                return f2;
            case 2:
            default:
                TaskFragment f3 = new TaskFragment();
                setTaskFragmentArguments(f3, doneTasks);
                doneTaskFragment = f3;
                return f3;
        }
    }

    private void setTaskFragmentArguments(TaskFragment fragment, List<Task> tasks) {
        Bundle bundle = new Bundle();
        bundle.putLong("projectId", projectId);
        bundle.putSerializable("tasks", (Serializable) tasks);
        fragment.setArguments(bundle);
    }

    @Override
    public void onTaskStatusChanged(Task task, TaskStatus newTaskStatus) {
        TaskFragment oldTaskFragment = getTaskFragment(task.getStatus());
        TaskFragment newTaskFragment = getTaskFragment(newTaskStatus);
        task.setStatus(newTaskStatus);
        removeTaskFromTaskFragment(task, oldTaskFragment);
        addTaskToTaskFragment(task, newTaskFragment);
    }


    public TaskFragment getTaskFragment(TaskStatus taskStatus) {
        switch (taskStatus) {
            case TO_DO:
                return toDoTaskFragment;
            case IN_PROGRESS:
                return inProgressTaskFragment;
            case DONE:
            default:
                return doneTaskFragment;
        }
    }

    public void addTaskToTaskFragment(Task task, TaskFragment taskFragment) {
        taskFragment.addTaskToRecyclerViewAdapter(task);
    }

    public void removeTaskFromTaskFragment(Task task, TaskFragment taskFragment) {
        taskFragment.removeTaskFromRecyclerViewAdapter(task);
    }


    public void addTaskToDo(Task task) {
        toDoTaskFragment.addTaskToRecyclerViewAdapter(task);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}