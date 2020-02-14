package com.marcinadd.projecty.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.chat.ChatApiService;
import com.marcinadd.projecty.chat.ChatHelper;
import com.marcinadd.projecty.chat.ChatMessageProjection;
import com.marcinadd.projecty.chat.ui.model.Dialog;
import com.marcinadd.projecty.chat.utils.MyAvatarLoader;
import com.marcinadd.projecty.client.MyOkHttpClient;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;

public class ChatDialogsListFragment extends Fragment
        implements RetrofitListener<List<ChatMessageProjection>>, DateFormatter.Formatter,
        DialogsListAdapter.OnDialogClickListener<Dialog> {

    private ChatDialogsListModel mViewModel;
    private View mView;
    private DialogsList dialogsListView;
    private DialogsListAdapter dialogsListAdapter;

    public static ChatDialogsListFragment newInstance() {
        return new ChatDialogsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat_dialogs_list, container, false);
        dialogsListView = mView.findViewById(R.id.dialogsList);

        ChatApiService.getInstance(getContext()).getChatMessageHistory(this);
        OkHttpClient client = MyOkHttpClient.getInstance(getContext()).getClient();
        dialogsListAdapter = new DialogsListAdapter<Dialog>(new MyAvatarLoader(getContext(), client));
        dialogsListAdapter.setDatesFormatter(this);
        dialogsListAdapter.setOnDialogClickListener(this);
        dialogsListView.setAdapter(dialogsListAdapter);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatDialogsListModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mViewModel = null;
        mView = null;
        dialogsListView = null;
        dialogsListAdapter = null;
    }

    @Override
    public void onResponseSuccess(List<ChatMessageProjection> response, @Nullable String TAG) {
        Collections.reverse(response);
        List<Dialog> dialogs = new ArrayList<>();
        for (ChatMessageProjection chatMessageProjection : response
        ) {
            Dialog dialog = ChatHelper.createDialogFromChatMessageProjection(chatMessageProjection, getContext());
            dialogs.add(dialog);
        }
        dialogsListAdapter.setItems(dialogs);
        mViewModel.setDialogs(dialogs);
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
        ChatDialogsListFragmentDirections.ActionNavChatToChatMessagesListFragment action =
                ChatDialogsListFragmentDirections.actionNavChatToChatMessagesListFragment();
        action.setUsername(dialog.getDialogName());
        Navigation.findNavController(mView).navigate(action);
    }
}