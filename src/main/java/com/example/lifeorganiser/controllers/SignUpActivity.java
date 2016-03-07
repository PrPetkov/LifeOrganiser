package com.example.lifeorganiser.src.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.Exceptions.UserManagerException;
import com.example.lifeorganiser.src.Models.user.UserManager;

public class SignUpActivity extends AppCompatActivity {

    UserManager userManager;

    EditText email;
    EditText username;
    EditText password;
    EditText repeatPassword;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.userManager = UserManager.getInstance();

        this.email = (EditText) findViewById(R.id.signUpEmailEditText);
        this.username = (EditText) findViewById(R.id.signUpUsernameEditText);
        this.password = (EditText) findViewById(R.id.signUpPassEditText);
        this.repeatPassword = (EditText) findViewById(R.id.signUpRepeatPassEditText);
        this.signUpButton = (Button) findViewById(R.id.signUpSignUpButton);

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = SignUpActivity.this.email.getText().toString();
                String username = SignUpActivity.this.username.getText().toString();
                String  password = SignUpActivity.this.password.getText().toString();
                String repeatPassword = SignUpActivity.this.repeatPassword.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "email can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.length() < UserManager.MIN_USERNAME_LENGTH){
                    Toast.makeText(SignUpActivity.this,
                            "username must be at least " + UserManager.MIN_USERNAME_LENGTH + " chars long",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repeatPassword)){
                    Toast.makeText(SignUpActivity.this, "password must be equal", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < UserManager.MIN_PASSWORD_LENGTH){
                    Toast.makeText(SignUpActivity.this,
                            "password must be at least " + UserManager.MIN_PASSWORD_LENGTH + " chars long",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    SignUpActivity.this.userManager.regUser(username, password, email);

                    Toast.makeText(SignUpActivity.this,
                            "Congratulations, you have registered successfully",
                            Toast.LENGTH_SHORT).show();
                } catch (UserManagerException e) {
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
