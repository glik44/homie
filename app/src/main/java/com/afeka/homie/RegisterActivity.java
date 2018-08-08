package com.afeka.homie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.afeka.homie.models.Model;
import com.afeka.homie.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import static com.afeka.homie.Main2ActivityImage.RESULT_SUCSSES;
import static com.afeka.homie.Main2ActivityImage.RESULT_SUCSSES_IMG;

public class RegisterActivity extends AppCompatActivity {


    String image_URL;
    Bitmap bitmap;
    EditText emailUser,nameUser,passUser;
    Intent intent;
    ScrollView sv;
    ProgressBar pb;
    ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameUser = (EditText) findViewById(R.id.name_register);
        passUser = (EditText) findViewById(R.id.password_register);
        emailUser = (EditText) findViewById(R.id.email_register);
        imgUser = (ImageView) findViewById(R.id.user_image_register);

        sv = (ScrollView) findViewById(R.id.register_form);
        pb = (ProgressBar) findViewById(R.id.register_progressbar);


        Button btn_regiser = (Button) findViewById(R.id.register_button);



        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });


        btn_regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 intent = new Intent(RegisterActivity.this,LoginActivity.class);

                intent.putExtra("name_User",nameUser.getText().toString());
                intent.putExtra("pass_user",passUser.getText().toString());

                intent.putExtra("email_user",emailUser.getText().toString());

                //we upload the user image URL to firebase, we get the link then we save the image link url

                //we will save also to internStorage the user as email (because its unique + extenstion of ".jpg")

                //convert ImageView to bitmap
             //    bitmap = ((BitmapDrawable)imgUser.getDrawable()).getBitmap();

                if (((nameUser.getText().toString().matches("") &&(passUser.getText().toString().matches("")) && (emailUser.getText().toString().matches("")))) || (emailUser.getText().toString().matches("")) || (passUser.getText().toString().matches("")) || (nameUser.getText().toString().matches("")))
                {
                    Toast.makeText(MyApplication.getMyContext(), "Dont leave empty fields!", Toast.LENGTH_SHORT).show();
                } else {

                    sv.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.VISIBLE);

                    attemptRegister();
                }
            }
        });


    }


    //calling the StartActivityForResult down to catch the image
    private void selectImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,RESULT_SUCSSES_IMG);
    }

    Uri path;
    //He is get the this from selectImage() up
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == RESULT_SUCSSES_IMG) && (data != null)) {

            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imgUser.setImageBitmap(bitmap);
                imgUser.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void attemptRegister() {


        Model.instance.Register(emailUser.getText().toString(), passUser.getText().toString(), new Model.RegisterCallback() {
            @Override
            public void onRegisterSu() {

                //if the user didnt provide a photo we put default
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nophoto);
                    }

                    //we get the user uID because we save the image at fb as uID.jpg and at locally uID.jpg
                    Model.instance.getCurrentUser(new Model.currentUserCallback() {
                        @Override
                        public void onConnected(final String userID) {

                            //save the image to firebase. and also to file locally storage it saves as  uID.jpg (as same name both locally and at fb) (uID is uniqe ID that firebase gives for each user)
                            Model.instance.saveImage(bitmap,userID, new Model.FirebaseStorageimageURL() {
                                @Override
                                public void onSuccess(Uri downloadUrl) {

                                    image_URL = downloadUrl.toString();

                                    //we save the details about the user on FB (images+context) and we save the image of the user locally(cache) as uid.jpg.
                                    Model.instance.addUser(new User(nameUser.getText().toString(), emailUser.getText().toString(), passUser.getText().toString(), image_URL, userID));

                                    pb.setVisibility(View.INVISIBLE);

                                    setResult(RESULT_SUCSSES, intent);
                                    finish();
                                    //we catch it at LoginActivity screen by onActivityResult func

                                }

                                @Override
                                public void onFailed() {


                                }
                            });
                            }

                    });
            }

            @Override
            public void onRegisterFa() {

              Toast.makeText(RegisterActivity.this, "Register failed." , Toast.LENGTH_SHORT).show();


            }
        });

    }



}
