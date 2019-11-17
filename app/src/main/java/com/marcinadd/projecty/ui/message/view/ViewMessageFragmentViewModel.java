package com.marcinadd.projecty.ui.message.view;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.message.model.Message;

public class ViewMessageFragmentViewModel extends ViewModel {
    private MutableLiveData<Message> message;

    public MutableLiveData<Message> getMessage() {
        if (message == null) {
            message = new MutableLiveData<>();
        }
        return message;
    }
}
