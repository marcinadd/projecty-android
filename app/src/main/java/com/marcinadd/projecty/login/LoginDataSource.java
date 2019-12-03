package com.marcinadd.projecty.login;

import com.marcinadd.projecty.client.NetworkClient;
import com.marcinadd.projecty.client.Token;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Retrofit;

public class LoginDataSource {
    public Result<LoggedInUser> login(String username, String password, String server) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(server);

        AuthClient authClient = retrofit.create(AuthClient.class);

        try {
            Token token = authClient.getToken("password", username, password).execute().body();
            if (token != null) {
                LoggedInUser user = authClient.getLoggedInUser("Bearer" + token.getAccessToken()).execute().body();
                //TODO Get avatar here
                Objects.requireNonNull(user).setToken(token);
                return new Result.Success<>(user);
            } else {
                throw new IOException("Invalid token");
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
