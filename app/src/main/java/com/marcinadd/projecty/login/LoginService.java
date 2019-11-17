package com.marcinadd.projecty.login;

import android.content.Context;

import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.UserNotLoggedListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginService {
    private static LoginService loginService;
    private AuthClient authClient;

    private LoginService(Context context) {
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        authClient = retrofit.create(AuthClient.class);
    }

    public static LoginService getInstance(Context context) {
        if (loginService == null) {
            loginService = new LoginService(context);
        }
        return loginService;
    }

    public void checkIfUserIsLogged(final UserNotLoggedListener listener) {
        authClient.getAuthenticatedUser().enqueue(new Callback<LoggedInUser>() {
            @Override
            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                if (!response.isSuccessful()) {
                    listener.onUserNotLogged();
                }
            }

            @Override
            public void onFailure(Call<LoggedInUser> call, Throwable t) {
                listener.onUserNotLogged();
            }
        });
    }

}
