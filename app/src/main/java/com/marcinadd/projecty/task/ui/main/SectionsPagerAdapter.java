package com.marcinadd.projecty.task.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.task.TaskType;
import com.marcinadd.projecty.task.fragment.TaskFragment;
import com.marcinadd.projecty.task.model.Task;

import java.util.List;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_to_do, R.string.tab_in_progress, R.string.tab_done};
    private final Context mContext;
    private List<Task> toDoTasks;
    private List<Task> inProgressTasks;
    private List<Task> doneTasks;

    public SectionsPagerAdapter(Context context, FragmentManager fm,
                                List<Task> toDoTasks,
                                List<Task> inProgressTasks,
                                List<Task> doneTasks
    ) {
        super(fm);
        mContext = context;
        this.toDoTasks = toDoTasks;
        this.inProgressTasks = inProgressTasks;
        this.doneTasks = doneTasks;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TaskFragment.newInstance(toDoTasks, TaskType.TO_DO);
            case 1:
                return TaskFragment.newInstance(inProgressTasks, TaskType.IN_PROGRESS);
            case 2:
            default:
                return TaskFragment.newInstance(doneTasks, TaskType.DONE);
        }
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