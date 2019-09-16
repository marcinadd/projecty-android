package com.marcinadd.projecty;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.marcinadd.projecty.client.NetworkClient;
import com.marcinadd.projecty.login.AuthClient;
import com.marcinadd.projecty.login.LoggedInUser;
import com.marcinadd.projecty.login.Token;

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
    String username = "user";
    String password = "user1234";
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.marcinadd.projecty", appContext.getPackageName());
    }

    @Test
    public void retrofitTest() throws IOException {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        AuthClient authClient = retrofit.create(AuthClient.class);
        Response<Token> tokenResponse = authClient.getToken("password", username, this.password).execute();
        assertEquals(200, tokenResponse.code());
    }

    @Test
    public void authorizedRetrofitTest() throws IOException {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        AuthClient authClient = retrofit.create(AuthClient.class);
        Response<Token> response = authClient.getToken("password", username, this.password).execute();
        assert response.body() != null;
        assertEquals(200, response.code());

        Retrofit authorizedRetrofit = new MockAuthorizedNetworkClient(response.body()).getRetrofitClient();
        AuthClient authClient1 = authorizedRetrofit.create(AuthClient.class);
        Response<LoggedInUser> response1 = authClient1.getAuthenticatedUser().execute();
        assert response1.body() != null;
        assertEquals(200, response1.code());
        assertEquals(username, response1.body().getDisplayName());

    }
}
