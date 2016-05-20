package com.shannor.theloyaltynetwork.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shannor.theloyaltynetwork.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText emailAddress;
    private EditText password; //Hash after receiving
    private Button submit;
    private FirebaseAuth mAuth;
    //Regex, Requires: 1 Capital, 1 Lower Case, 1 Special Char, 1 Number, and at least length 8
    private String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        pattern = Pattern.compile(regexPassword);

        emailAddress = (EditText)findViewById(R.id.user_email);
        password = (EditText)findViewById(R.id.user_password);
        submit = (Button)findViewById(R.id.confirm_creation);

        emailAddress.requestFocus();
        showKeyBoard();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( emailAddress.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty()){
                    showKeyBoard();
                    emailAddress.requestFocus();
                    Dialog d = createErrorDialog("Empty Fields","Email or Password was empty");
                    d.show();

                }else{
                    matcher = pattern.matcher(password.getText().toString());
                    if(matcher.matches()) {
                        hideKeyBoard();
                        createAccount(emailAddress.getText().toString(), password.getText().toString());
                        finish();
                    }else{
                        Dialog d = createErrorDialog("Too weak of password.","Password did not meet the criteria.");
                        d.show();
                        password.setText("");
                        password.requestFocus();
                        showKeyBoard();
                    }
                }
            }
        });

    }

    public void showKeyBoard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void hideKeyBoard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getBaseContext(),"Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    public Dialog createErrorDialog(String title, String positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(positive);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Purposely empty
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
