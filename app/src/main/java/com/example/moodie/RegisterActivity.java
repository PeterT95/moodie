package com.example.moodie;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String username;
    private String password;
    private String email;
    private String country;

    ConstraintLayout background;


    // Shuts down keyboard if it is up
    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.registerBackground)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Countries Spinner
        Spinner uinputCountry = findViewById(R.id.regCountryList);

        Locale[] locale = Locale.getAvailableLocales();

        ArrayList<String> countriesList = new ArrayList<>();
        String country;
        for (Locale loc : locale)
        {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countriesList.contains(country))
            {
                countriesList.add(country);
            }
        }
        Collections.sort(countriesList, String.CASE_INSENSITIVE_ORDER);

        ArrayList<String> countries = new ArrayList<String>();
        countries.add("Country");
        countries.addAll(countriesList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries) {
            @Override
            public boolean isEnabled(int position)
            {
                if (position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0)
                {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else
                {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        uinputCountry.setAdapter(adapter);


        // Terms and conditions
        TextView termsCond = findViewById(R.id.termsCondTitle);

        termsCond.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RegisterActivity.this, TermsActivity.class);
                startActivity(intent);
            }
        });


        Button registerButton = findViewById(R.id.regRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                register();

            }
        });


        // Minimizes keyboard when pressed
        background = findViewById(R.id.registerBackground);
        background.setOnClickListener(this);


    } // oncreate


    public void captureUserInfo()
    {

        EditText uinputName = (EditText) findViewById(R.id.regUserNameBox);
        EditText uinputPass = (EditText) findViewById(R.id.regPasswdBox);
        EditText uinputEmail = (EditText) findViewById(R.id.regEmailBox);
        Spinner uinputCountry = (Spinner) findViewById(R.id.regCountryList);

        this.username = uinputName.getText().toString();
        this.password = uinputPass.getText().toString();
        this.email = uinputEmail.getText().toString();
        this.country = uinputCountry.getSelectedItem().toString();
    }

    public void register()
    {

        captureUserInfo();

        if (this.username.equals("") || this.password.equals("") || this.email.equals("") || this.country.equals(""))
        {
            Toast.makeText(RegisterActivity.this, "All fields are required to register!", Toast.LENGTH_SHORT).show();

        }
        else
        {

            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.put("Country", country);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e)
                {
                    if (e == null)
                    {
                        Log.i("Signup", "Successful");

                    }
                    else
                    {
                        Log.i("Signup", "Failed: " + e.toString());
                        Toast.makeText(RegisterActivity.this, "Failed to register! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
