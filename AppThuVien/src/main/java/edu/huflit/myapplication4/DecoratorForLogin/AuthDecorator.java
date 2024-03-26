package edu.huflit.myapplication4.DecoratorForLogin;

import android.view.View;

public class AuthDecorator implements LoginFunctionality {
    private LoginFunctionality loginFunctionality;

    public AuthDecorator(LoginFunctionality loginFunctionality) {
        this.loginFunctionality = loginFunctionality;
    }
    @Override
    public void login(View view, String account, String password) {
        loginFunctionality.login(view, account, password);
    }

}
