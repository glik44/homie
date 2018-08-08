package com.afeka.homie.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.afeka.homie.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ben on 6/4/17.
 */

public class ModelSQL extends  SQLiteOpenHelper{

    static final String APARMENT_TABLE = "apartments";

    static final String APARTMENT_ID = "aprid";
    static final String APARTMENT_COUNTRY = "country";
    static final String APARTMENT_STREET = "street";
    static final String APARTMENT_PRODUCTYPE = "producType";
    static final String APARTMENT_price = "price";
    static final String APARTMENT_latx = "latitude";
    static final String APARTMENT_longy = "longitude";

    static final String APARTMENT_IMAGE_URL = "imageUrl";


    public ModelSQL (Context context) {
        super(context, "database.db", null, 1);

    }


    public void addApartment(SQLiteDatabase db,Apartment apartment) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(APARTMENT_ID, apartment.id);
        values.put(APARTMENT_COUNTRY, apartment.country);
        values.put(APARTMENT_STREET,apartment.street);
        values.put(APARTMENT_PRODUCTYPE,apartment.product_type);
        values.put(APARTMENT_price,apartment.pricemonth);
        values.put(APARTMENT_latx,apartment.xlat);
        values.put(APARTMENT_longy,apartment.ylongt);
        values.put(APARTMENT_IMAGE_URL, apartment.aparmentURL);

        Log.d("TAG9","The Apartment has been sucssfully saved,SQLite");

        // Insert the new row, returning the primary key value of the new row
        db.insert(APARMENT_TABLE, APARTMENT_ID, values);
    }


    interface ApartmentListenerSQL {

            public void onSQLsucsses(ArrayList<Apartment> apartment);
            public void onSQLfail();

    }

    public void getAllApartments(SQLiteDatabase db, ApartmentListenerSQL listenerSQL) {
        Cursor cursor = db.query(APARMENT_TABLE, null, null, null, null, null, null);
        ArrayList<Apartment> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(APARTMENT_ID);
            int idCountry = cursor.getColumnIndex(APARTMENT_COUNTRY);
            int idStreet = cursor.getColumnIndex(APARTMENT_STREET);
            int producTypendex = cursor.getColumnIndex(APARTMENT_PRODUCTYPE);
            int priceIndex = cursor.getColumnIndex(APARTMENT_price);
            int latitudeIndex = cursor.getColumnIndex(APARTMENT_latx);
            int longitudeindex = cursor.getColumnIndex(APARTMENT_longy);

            int imageUrlIndex = cursor.getColumnIndex(APARTMENT_IMAGE_URL);

            do {
                Apartment st = new Apartment();
                st.id = cursor.getString(idIndex);
                st.country = cursor.getString(idCountry);
                st.street = cursor.getString(idStreet);
                st.product_type = cursor.getString(producTypendex);
                st.pricemonth = cursor.getString(priceIndex);
                st.xlat = cursor.getString(latitudeIndex);
                st.ylongt = cursor.getString(longitudeindex);
                st.aparmentURL = cursor.getString(imageUrlIndex);

                list.add(st);

            } while (cursor.moveToNext());
        }

        Log.d("TAG9", "THERE IS " + " size of the SQlite" + String.valueOf(list.size()));

        listenerSQL.onSQLsucsses(list);
    }




    //get all the coordinateApartments for the big map
    public  ArrayList<Apartment> getAllCoordinateAparments(SQLiteDatabase db) {

        Cursor cursor = db.query(APARMENT_TABLE, null, null, null, null, null, null);

        ArrayList<Apartment> coodrinateArray = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(APARTMENT_ID);

            int idCountry = cursor.getColumnIndex(APARTMENT_COUNTRY);
            int idStreet = cursor.getColumnIndex(APARTMENT_STREET);

            int latitudeIndex = cursor.getColumnIndex(APARTMENT_latx);
            int longitudeindex = cursor.getColumnIndex(APARTMENT_longy);


            do {
                Apartment st = new Apartment();

                st.id = cursor.getString(idIndex);
                st.country = cursor.getString(idCountry);
                st.street = cursor.getString(idStreet);
                st.xlat = cursor.getString(latitudeIndex);
                st.ylongt = cursor.getString(longitudeindex);

                coodrinateArray.add(st);

            } while (cursor.moveToNext());

        }

        return coodrinateArray;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + APARMENT_TABLE +
                " (" +
                APARTMENT_ID + " TEXT PRIMARY KEY, " +
                APARTMENT_COUNTRY + " TEXT, " +
                APARTMENT_STREET + " TEXT, " +
                APARTMENT_PRODUCTYPE + " TEXT, " +
                APARTMENT_price + " TEXT, " +
                APARTMENT_latx + " NUMBER, " +
                APARTMENT_longy + " NUMBER, " +

                APARTMENT_IMAGE_URL + " TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop " + APARMENT_TABLE + ";");
        onCreate(db);
    }

    public void deleteDataBase ( ) {

        MyApplication.getMyContext().deleteDatabase(APARMENT_TABLE);
    }


    //FILES
    //Save to Internal Storage
    //Name is the id of the file , we save images as 1.jpg (for apartment1 and etc..)

    public  void saveImageToInternalStorage(Context context, Bitmap bitmapImage, String name, String extension, LocalImageListener listener){
        name=name + extension;
        Log.d("TAG",name);
        FileOutputStream out;

        File file = context.getFilesDir();
        Log.d("TAG",file.getAbsolutePath());
        try {

            out = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, out);

            listener.onSu(bitmapImage);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFa();
        }
    }

//    interface imageListener{
//
//        public void onSu(Bitmap bmp);
//        public void onFu();
//
//
//    }

    interface LocalImageListener {

        public void onSu(Bitmap bmp);
        public void onFa();

    }


    //read from Internal Storage
    public void readImageFileFromInternalStorage(Context context, String name, String extension, LocalImageListener listener) {

        String name1=name + extension;
        String yourFilePath = context.getFilesDir() + "/" + name1;

        File filePath=new File(yourFilePath);

        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(filePath));

        if (bitmap == null) {

            Log.d("TAG8", "local image not found,going to Firebase..");
            listener.onFa();


        } else {

            Log.d("TAG8","local image found! getting them..");
            listener.onSu(bitmap);

        }
        //return bitmap;
    }

}
