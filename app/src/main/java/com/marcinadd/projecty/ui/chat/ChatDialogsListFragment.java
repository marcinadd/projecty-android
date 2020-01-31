package com.marcinadd.projecty.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.chat.ChatApiService;
import com.marcinadd.projecty.chat.ChatMessageProjection;
import com.marcinadd.projecty.chat.ui.model.Dialog;
import com.marcinadd.projecty.helper.DialogHelper;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatDialogsListFragment extends Fragment
        implements RetrofitListener<List<ChatMessageProjection>>, DateFormatter.Formatter,
        DialogsListAdapter.OnDialogClickListener<Dialog> {

    private ChatViewModel mViewModel;
    private DialogsList dialogsListView;
    private DialogsListAdapter dialogsListAdapter;

    public static ChatDialogsListFragment newInstance() {
        return new ChatDialogsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_dialogs_list, container, false);
        dialogsListView = view.findViewById(R.id.dialogsList);

        ChatApiService.getInstance(getContext()).getChatMessageHistory(this);

        dialogsListAdapter = new DialogsListAdapter((imageView, url, payload) -> {
            //TODO Add image loader here
        });
        dialogsListAdapter.setDatesFormatter(this);
        dialogsListAdapter.setOnDialogClickListener(this);
        dialogsListView.setAdapter(dialogsListAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResponseSuccess(List<ChatMessageProjection> response, @Nullable String TAG) {
        Collections.reverse(response);
        for (ChatMessageProjection chatMessageProjection : response
        ) {
            Dialog dialog = DialogHelper.createDialogFromChatMessageProjection(chatMessageProjection, getContext());
            dialogsListAdapter.upsertItem(dialog);
        }
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        Log.e("Cliked! on dialog", dialog.getId());
    }
}
