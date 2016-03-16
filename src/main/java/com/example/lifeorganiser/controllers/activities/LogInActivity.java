package com.example.lifeorganiser.src.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.Exceptions.UserManagerException;
import com.example.lifeorganiser.src.Models.user.UserManager;

public class LogInActivity extends AppCompatActivity {

    private static Context context;
    private UserManager userManager;

    private Button logInButton;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private TextView signUpTextView;
    private TextView forgottenPWTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        LogInActivity.context = getApplicationContext();

        this.userManager = UserManager.getInstance();

        this.logInButton = (Button) findViewById(R.id.loginActivityLogInUp);
        this.userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        this.passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        this.signUpTextView = (TextView) findViewById(R.id.signUpTextView);
        this.forgottenPWTextView = (TextView) findViewById(R.id.forgottenPWView);

        this.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);

                String userename = LogInActivity.this.userNameEditText.getText().toString();
                String password = LogInActivity.this.passwordEditText.getText().toString();

                if (userename == null || userename.isEmpty()){
                    Toast.makeText(LogInActivity.this, "Username must have a value", Toast.LENGTH_LONG).show();
                    return;
                }

                if (password == null || password.isEmpty()){
                    Toast.makeText(LogInActivity.this, "Password must have a value", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    LogInActivity.this.userManager.logUser(userename, password);
                } catch (UserManagerException e) {
                    Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                startActivity(intent);
            }
        });

        this.signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);

                startActivity(intent);
            }
        });
    }

    public static Context getContext(){
        return context;
    }
}
