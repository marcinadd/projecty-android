package com.marcinadd.projecty.task.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.TaskStatusChangedListener;
import com.marcinadd.projecty.task.model.Task;

import java.util.List;


public class TaskFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private TaskStatusChangedListener taskStatusChangedListener;
    private List<Task> tasks;
    private long projectId;
    private MyTaskRecyclerViewAdapter myTaskRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    public TaskFragment(List<Task> tasks, long projectId, TaskStatusChangedListener taskStatusChangedListener) {
        this.tasks = tasks;
        this.projectId = projectId;
        this.taskStatusChangedListener = taskStatusChangedListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list_content, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            myTaskRecyclerViewAdapter = new MyTaskRecyclerViewAdapter(tasks, projectId, mListener, taskStatusChangedListener);
            recyclerView.setAdapter(myTaskRecyclerViewAdapter);
        }
        return view;
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

    public void addTaskToRecyclerViewAdapter(Task task) {
        myTaskRecyclerViewAdapter.addItem(task);
    }

    public void removeTaskFromRecyclerViewAdapter(Task task) {
        myTaskRecyclerViewAdapter.removeItem(task);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Task item);
    }
}
