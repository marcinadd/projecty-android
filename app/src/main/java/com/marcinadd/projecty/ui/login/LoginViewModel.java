package com.marcinadd.projecty.ui.login;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.login.LoggedInUser;
import com.marcinadd.projecty.login.LoginRepository;
import com.marcinadd.projecty.login.Result;

import java.lang.ref.WeakReference;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(final String username, final String password, final String host, Context context) {
        // can be launched in a separate asynchronous job
        new LoginTask(context).execute(username, password, host);
    }

    public void loginDataChanged(String username, String password, String server) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password, null));
        } else if (!isServerValid(server)) {
            loginFormState.setValue(new LoginFormState(null, null, R.string.invalid_server));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isServerValid(String server) {
        String REGEX = "http://(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]):[0-9]+$";
        return server.matches(REGEX);
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    class LoginTask extends AsyncTask<String, Void, Result<LoggedInUser>> {
        private WeakReference<Context> contextRef;

        LoginTask(Context context) {
            this.contextRef = new WeakReference<>(context);
        }

        @Override
        protected Result<LoggedInUser> doInBackground(String... strings) {
            return loginRepository.login(strings[0], strings[1], strings[2], contextRef.get());
        }

        @Override
        protected void onPostExecute(Result<LoggedInUser> result) {
            if (result instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            } else {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        }
    }
}
