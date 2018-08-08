package com.afeka.homie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afeka.homie.models.Model;
import com.afeka.homie.models.ModelFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.afeka.homie.AddApartmentFragment.REQUEST_ADD_ID;
import static com.afeka.homie.Main2ActivityImage.RESULT_SUCSSES;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText email;
    private EditText password;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       // mLoginFormView = findViewById(R.id.login_form);

        //mProgressView = findViewById(R.id.login_progress);

        // Set up the login form.
        email = (EditText) findViewById(R.id.email);

        password = (EditText) findViewById(R.id.password);

        Button signInButton = (Button) findViewById(R.id.sign_in_button);

        Button registerButton = (Button) findViewById(R.id.register_button);

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((email.getText().toString().matches("") && (password.getText().toString().matches("")))) || (password.getText().toString().matches("")) || (email.getText().toString().matches(""))) {
                    Toast.makeText(MyApplication.getMyContext(), "Dont leave empty fields!", Toast.LENGTH_SHORT).show();
                } else

                {
                    attemptLogin();

                }


            }
        });

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, RESULT_SUCSSES);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == REQUEST_ADD_ID) && (data != null)) {

            Log.d("TAG", "success for RegisterActivity to LoginActivity: onActivityResult func");

            Toast.makeText(LoginActivity.this, "Registration Complete, Log in", Toast.LENGTH_SHORT).show();

            //imgview.setImageBitmap(bmp);
            //imgview.setVisibility(View.VISIBLE);

        }

    }


    private void attemptLogin() {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        Model.instance.Login(email.getText().toString(), password.getText().toString(), new Model.LoginCallback() {

            @Override
            public void onLoginSu(String msg) {

                progressDialog.dismiss();
                Log.d("TAG", msg);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLoginFa(String msg) {

                progressDialog.dismiss();

                Log.d("TAG", msg);
                Toast.makeText(LoginActivity.this, "Authentication failed-check ur email or password", Toast.LENGTH_SHORT).show();

            }
        });

    }




}