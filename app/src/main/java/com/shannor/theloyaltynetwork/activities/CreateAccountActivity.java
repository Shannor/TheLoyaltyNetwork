package com.shannor.theloyaltynetwork.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;
import com.shannor.theloyaltynetwork.R;

public class CreateAccountActivity extends AppCompatActivity {

    EditText userName;
    EditText emailAddress;
    EditText password; //Hash after receiving
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = (EditText)findViewById(R.id.user_name);
        emailAddress = (EditText)findViewById(R.id.user_email);
        password = (EditText)findViewById(R.id.user_password); //Hash
        submit = (Button)findViewById(R.id.confirm_creation);

        userName.requestFocus();
        showKeyBoard();

        //TODO: Server Registration Here
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( userName.getText().toString().isEmpty() ||
                        emailAddress.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty()){
                    //Show Error message that something is null
                }else{
                    hideKeyBoard();
                    finish();
                }
                //Check Server to see if already Registered
                //Create an alert if email is in use
                //if not then add to server
                //Create alert if information isnt strong enough
            }
        });

    }

    public void showKeyBoard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void hideKeyBoard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
