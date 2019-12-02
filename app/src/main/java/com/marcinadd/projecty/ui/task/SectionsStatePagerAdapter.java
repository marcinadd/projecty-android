package com.marcinadd.projecty.ui.task;

import android.content.Context;
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


    SectionsStatePagerAdapter(Context context, FragmentManager fm,
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
                toDoTaskFragment = TaskFragment.newInstance(toDoTasks, projectId);
                return toDoTaskFragment;
            case 1:
                inProgressTaskFragment = TaskFragment.newInstance(inProgressTasks, projectId);
                return inProgressTaskFragment;
            case 2:
            default:
                doneTaskFragment = TaskFragment.newInstance(doneTasks, projectId);
                return doneTaskFragment;
        }
    }

    @Override
    public void onTaskStatusChanged(Task task, TaskStatus newTaskStatus) {
        TaskFragment oldTaskFragment = getTaskFragment(task.getStatus());
        TaskFragment newTaskFragment = getTaskFragment(newTaskStatus);
        task.setStatus(newTaskStatus);
        removeTaskFromTaskFragment(task, oldTaskFragment);
        addTaskToTaskFragment(task, newTaskFragment);
    }


    private TaskFragment getTaskFragment(TaskStatus taskStatus) {
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

    private void addTaskToTaskFragment(Task task, TaskFragment taskFragment) {
        taskFragment.addTaskToRecyclerViewAdapter(task);
    }

    private void removeTaskFromTaskFragment(Task task, TaskFragment taskFragment) {
        taskFragment.removeTaskFromRecyclerViewAdapter(task);
    }


    void addTaskToDo(Task task) {
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