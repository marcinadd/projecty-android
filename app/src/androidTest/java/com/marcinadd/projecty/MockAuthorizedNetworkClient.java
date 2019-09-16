package com.marcinadd.projecty;

import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.login.Token;

public class MockAuthorizedNetworkClient extends AuthorizedNetworkClient {
    private Token token;

    public MockAuthorizedNetworkClient(Token token) {
        this.token = token;
    }

    @Override
    protected String getToken() {
        return token.getAccessToken();
    }
}
