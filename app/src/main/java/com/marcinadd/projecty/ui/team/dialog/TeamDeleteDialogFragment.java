package com.marcinadd.projecty.ui.team.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.TeamService;
import com.marcinadd.projecty.team.model.Team;

public class TeamDeleteDialogFragment extends DialogFragment implements RetrofitListener<Void> {
    private static final String TEAM = "TEAM";
    private Team team;
    private OnTeamDeletedListener onTeamDeletedListener;

    public static TeamDeleteDialogFragment newInstance(Team team) {
        TeamDeleteDialogFragment fragment = new TeamDeleteDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TEAM, team);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnTeamDeletedListener(OnTeamDeletedListener onTeamDeletedListener) {
        this.onTeamDeletedListener = onTeamDeletedListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            team = (Team) getArguments().getSerializable(TEAM);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.team_delete_confirm))
                .setPositiveButton(R.string.ok, onPositiveButtonClickListener())
                .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    Dialog.OnClickListener onPositiveButtonClickListener() {
        return (dialog, which) -> {
            TeamService.getInstance(getContext()).deleteTeam(team.getId(), this);
        };
    }

    @Override
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        onTeamDeletedListener.onTeamDeleted();
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnTeamDeletedListener {
        void onTeamDeleted();
    }
}
