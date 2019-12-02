package com.marcinadd.projecty.ui.team.manage.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.TeamService;
import com.marcinadd.projecty.team.model.Team;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.ArrayList;
import java.util.List;

public class AddTeamRolesDialogFragment extends DialogFragment implements RetrofitListener<List<TeamRole>> {
    private Team team;
    private LinearLayout linearLayout;
    private OnTeamRolesAddedListener onTeamRolesAddedListener;

    public static AddTeamRolesDialogFragment newInstance(Team team) {
        AddTeamRolesDialogFragment fragment = new AddTeamRolesDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("team", team);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnTeamRolesAddedListener(OnTeamRolesAddedListener onTeamRolesAddedListener) {
        this.onTeamRolesAddedListener = onTeamRolesAddedListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            team = (Team) getArguments().getSerializable("team");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_team_role_add, null);
//        final EditText editText = view.findViewById(R.id.projectrole_username_edit_text);
        linearLayout = view.findViewById(R.id.team_role_add_layout);
        ImageView imageView = view.findViewById(R.id.team_role_add_image_view);
        imageView.setOnClickListener(onImageViewClick());
        addEditText();
        builder.setMessage(R.string.add_role)
                .setPositiveButton(R.string.ok, onSuccessButtonClicked())
                .setNegativeButton(R.string.cancel, null)
                .setView(view);
        return builder.create();
    }

    DialogInterface.OnClickListener onSuccessButtonClicked() {
        return (dialog, which) -> {
            List<String> usernames = getUsernamesToList();
            TeamService.getInstance(getContext()).addUsers(team.getId(), usernames, this);
        };
    }

    public List<String> getUsernamesToList() {
        List<String> usernames = new ArrayList<>();
        int n = linearLayout.getChildCount();
        for (int i = 0; i < n; i++) {
            EditText editText = (EditText) linearLayout.getChildAt(i);
            String username = editText.getText().toString();
            if (!username.isEmpty()) {
                usernames.add(username);
            }
        }
        return usernames;
    }

    public void addEditText() {
        EditText editText = new EditText(getContext());
        editText.setHint(R.string.enter_username);
        linearLayout.addView(editText);
    }

    private View.OnClickListener onImageViewClick() {
        return v -> {
            if (linearLayout.getChildCount() < 5) {
                addEditText();
            }
        };
    }

    @Override
    public void onResponseSuccess(List<TeamRole> response, @Nullable String TAG) {
        onTeamRolesAddedListener.onTeamRolesAdded(response);
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnTeamRolesAddedListener {
        void onTeamRolesAdded(List<TeamRole> teamRoles);
    }
}
