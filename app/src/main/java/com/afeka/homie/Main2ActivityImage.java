package com.afeka.homie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Main2ActivityImage extends AppCompatActivity {

    public static int RESULT_SUCSSES = 1;
    public static int RESULT_SUCSSES_IMG = 2;

    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_image);

        Button savebtn = (Button) findViewById(R.id.btnSaveActivity2);
        Button choosebtnimage = (Button) findViewById(R.id.btnChooseImageActivity2);
        imgView = (ImageView) findViewById(R.id.imgView_activity2);

        choosebtnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main2ActivityImage.this,AddApartmentFragment.class);

//                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();



                //compress the bitmap size , bitmap as an byte's array

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra("bitmapImage", bytes);



                setResult(RESULT_SUCSSES,intent);
                finish();
            }
        });
    }

    Uri path;
    Bitmap bitmap;
    Bitmap newBitmap;
    //he catch from the selectedimage (function down)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == RESULT_SUCSSES_IMG) && (data != null)){

             path = data.getData();
            try {


                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                Log.d("TAG","SIZE OF THE Bitmap before: " +  String.valueOf(bitmap.getByteCount()));

                newBitmap = scaleDownBitmap(bitmap,100,this);
                Log.d("TAG","SIZE OF THE Bitmap after: " +  String.valueOf(newBitmap.getByteCount()));

                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }
    private void selectImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,RESULT_SUCSSES_IMG);
    }




}
