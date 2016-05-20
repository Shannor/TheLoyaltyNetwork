package com.shannor.theloyaltynetwork.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.shannor.theloyaltynetwork.R;
import com.shannor.theloyaltynetwork.mangers.PostManager;
import com.shannor.theloyaltynetwork.mangers.SessionManager;
import com.shannor.theloyaltynetwork.model.User;

public class CreatePostActivity extends AppCompatActivity {

    private PostManager postManager = PostManager.getInstance();
    private EditText title;
    private EditText body;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.post_title_view);
        body = (EditText)findViewById(R.id.post_body_view);

        builder = new AlertDialog.Builder(this);
        title.requestFocus();
        showKeyBoard();

        builder.setMessage(R.string.empty_text)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Just a notification that nothing is filled out
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_post) {
            //Set Alert Box to show information
            AlertDialog dialog = builder.create();
            //Error check to make sure nothing is empty
            if (title.getText().toString().isEmpty() || body.getText().toString().isEmpty()){
                dialog.show();
            }else {
                //TODO: Add current user functionality
                //Just show a substring of the Body text
                postManager.addContent(new User("Test"),title.getText().toString(),body.getText().toString());
                setResult(Activity.RESULT_OK);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void showKeyBoard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
