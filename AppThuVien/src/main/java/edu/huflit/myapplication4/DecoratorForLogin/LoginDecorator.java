package edu.huflit.myapplication4.DecoratorForLogin;

import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class LoginDecorator extends AuthDecorator {

    public LoginDecorator(LoginFunctionality loginFunctionality) {
        super(loginFunctionality);
    }

    @Override
    public void login(View view, String account, String password) {
        if (validateCredentials(account, password)) {
        super.login(view, account, password);
        }else if (TextUtils.isEmpty(account)) {
            Snackbar.make(view, "Tài khoản không được để trống", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else if (TextUtils.isEmpty(password)) {
            Snackbar.make(view, "mật khẩu không được để trống", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else {
            // Xử lý khi thông tin đăng nhập không hợp lệ
            Snackbar.make(view, "Tài khoản và mật khẩu không được để trống", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
    private boolean validateCredentials(String account, String password) {
        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password);
    }
}
