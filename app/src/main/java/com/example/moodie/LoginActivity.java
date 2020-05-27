package com.example.moodie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    Button loginButton;
    ConstraintLayout background;

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            login();
            return true;
        }
        return false;
    }


    // Shuts down keyboard if it is up
    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.loginBackground)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // To register Screen

        TextView toRegister = findViewById(R.id.registerTxt);
        toRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Minimizes keyboard when pressed
        background = findViewById(R.id.loginBackground);
        background.setOnClickListener(this);


        // To login
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                login();
            }
        });

        // Can login by pressing enter in Password box
        EditText pwordBox = findViewById(R.id.passwdBox);
        pwordBox.setOnKeyListener(this);

    }


    private void login()
    {

        EditText userBox = findViewById(R.id.usernameBox);
        EditText pwordBox = findViewById(R.id.passwdBox);

        String username = userBox.getText().toString();
        String password = pwordBox.getText().toString();

        if (userBox.getText().toString().equals("") || pwordBox.toString().equals(""))
        {
            Toast.makeText(this, "Username and password must not be empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ParseUser parseUser = new ParseUser();

            parseUser.setUsername(userBox.getText().toString());
            parseUser.setPassword(pwordBox.getText().toString());

            parseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e)
                {

                    if (user != null)
                    {
                        Log.i("Login", "Successful");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

