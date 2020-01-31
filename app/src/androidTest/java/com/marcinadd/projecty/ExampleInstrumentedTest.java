package com.marcinadd.projecty;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.marcinadd.projecty.client.NetworkClient;
import com.marcinadd.projecty.client.Token;
import com.marcinadd.projecty.login.AuthClient;
import com.marcinadd.projecty.login.LoggedInUser;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private final String HOST = "http://10.0.2.2:8080";
    private final String USERNAME = "user";
    private final String PASSWORD = "user1234";
    private Context appContext;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.marcinadd.projecty", appContext.getPackageName());
    }

    @Test
    public void retrofitTest() throws IOException {
        Retrofit retrofit = NetworkClient.getRetrofitClient(HOST, true);
        AuthClient authClient = retrofit.create(AuthClient.class);
        Response<Token> tokenResponse = authClient.getToken("PASSWORD", USERNAME, this.PASSWORD).execute();
        assertEquals(200, tokenResponse.code());
    }

    @Test
    public void authorizedRetrofitTest() throws IOException {
        Retrofit retrofit = NetworkClient.getRetrofitClient(HOST, true);
        AuthClient authClient = retrofit.create(AuthClient.class);
        Response<Token> response = authClient.getToken("password", USERNAME, this.PASSWORD).execute();
        assert response.body() != null;
        assertEquals(200, response.code());

        Retrofit authorizedRetrofit = new MockAuthorizedNetworkClient(response.body()).getRetrofitClient();
        AuthClient authClient1 = authorizedRetrofit.create(AuthClient.class);
        Response<LoggedInUser> response1 = authClient1.getAuthenticatedUser().execute();
        assert response1.body() != null;
        assertEquals(200, response1.code());
        assertEquals(USERNAME, response1.body().getDisplayName());

    }
}
