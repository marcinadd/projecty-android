package com.marcinadd.projecty.ui.team.manage.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.internal.LinkedTreeMap;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.TeamService;
import com.marcinadd.projecty.team.model.Team;

import java.util.Map;

public class ChangeTeamNameDialogFragment extends DialogFragment implements RetrofitListener<Void> {
    private Team team;
    private OnTeamNameChangedListener onTeamNameChangedListener;

    public static ChangeTeamNameDialogFragment newInstance(Team team) {
        ChangeTeamNameDialogFragment fragment = new ChangeTeamNameDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("team", team);
        fragment.setArguments(bundle);
        return fragment;

    }

    public void setOnTeamNameChangedListener(OnTeamNameChangedListener onTeamNameChangedListener) {
        this.onTeamNameChangedListener = onTeamNameChangedListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            team = (Team) getArguments().getSerializable("team");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_change_name, null);
        final EditText editText = view.findViewById(R.id.change_name_edit_text);
        builder.setMessage(R.string.text_view_change_name)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    final String newTeamName = editText.getText().toString();
                    Map<String, String> fields = new LinkedTreeMap<>();
                    fields.put("name", newTeamName);
                    team.setName(newTeamName);
                    TeamService.getInstance(getContext()).editTeam(team.getId(), fields, ChangeTeamNameDialogFragment.this);

                })
                .setNegativeButton(R.string.cancel, null)
                .setView(view);
        return builder.create();
    }

    @Override
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        onTeamNameChangedListener.onTeamNameChanged(team.getName());
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnTeamNameChangedListener {
        void onTeamNameChanged(String newTeamName);
    }
}
