package edu.huflit.myapplication4.DecoratorForLogin;

import android.util.Log;
import android.view.View;

public class LoggingDecorator extends AuthDecorator {
    public LoggingDecorator(LoginFunctionality loginFunctionality) {
        super(loginFunctionality);
    }

    @Override
    public void login(View view, String account, String password) {
        // Ghi log trước khi thực hiện xác thực đăng nhập
        Log.d("LoggingAuthValidationDecorator", "Validating login credentials...");

        // Gọi phương thức đăng nhập của Decorator hoặc Component
        super.login(view, account, password);

        // Ghi log sau khi thực hiện xác thực đăng nhập
        Log.d("LoggingAuthValidationDecorator", "Login credentials validated.");
    }
}
