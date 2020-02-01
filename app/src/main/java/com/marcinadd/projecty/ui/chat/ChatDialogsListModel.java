package com.marcinadd.projecty.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.chat.ui.model.Dialog;

import java.util.List;

public class ChatDialogsListModel extends ViewModel {
    MutableLiveData<List<Dialog>> mDialogs;

    public ChatDialogsListModel() {
        mDialogs = new MutableLiveData<>();
    }

    public LiveData<List<Dialog>> getDialogs() {
        return mDialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        mDialogs.setValue(dialogs);
    }

}
