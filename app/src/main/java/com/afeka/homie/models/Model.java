package com.afeka.homie.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.afeka.homie.MyApplication;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 6/4/17.
 */


//Singleton
public class Model  {



    public static final Model instance  = new Model();

    private ModelMem modelMem;
    private ModelSQL modelSQL;
    private ModelFirebase modelFirebase;

    private Model() {

         modelMem = new ModelMem();
         modelSQL = new ModelSQL(MyApplication.getMyContext());
         modelFirebase = new ModelFirebase();
    }


    //adding the Apartment to FireBase and SQLite
    public void addApartment (Apartment apartment) {

        //TODO we need to check if it exist on SQlite if no we add it, and then add it to Firebase, and from the other side FB not exist add it and add it to Sqlite.

        //check if it exist on modelSQL


       // modelSQL.addApartment(modelSQL.getWritableDatabase(),apartment);
        modelFirebase.addApartmentToFireBase(apartment);
    }


    public void addUser (User usr){

        modelFirebase.addUserToFireBase(usr);

    }



    public interface FirebaseStorageimageURL {
        public void onSuccess(Uri downloadUrl);
        public void onFailed();

    }


    /**
     *bitmap the image we want to save
     *  string id the image locally saved as uID of the user (uniqe id firebase give) as uID.jpg also at firebase
     *
     // remote Firebase  db storage , and local  db storage
     */

    public void saveImage (final Bitmap bitmap, final String id, final FirebaseStorageimageURL firebasestorage) {

        //asyn callback func
        modelSQL.saveImageToInternalStorage(MyApplication.getMyContext(), bitmap, id, ".jpg", new ModelSQL.LocalImageListener() {
            @Override
            public void onSu(Bitmap bmp) {

                //asyn callback func
                modelFirebase.saveImageToFireBase(bitmap, id, new ModelFirebase.FirebaseStorageimageURL()
                {
                    @Override
                    public void onSuccess(Uri downloadUrl) {

                        firebasestorage.onSuccess(downloadUrl);

                    }

                    @Override
                    public void onFailed() {

                        firebasestorage.onFailed();
                    }
                });

            }

            @Override
            public void onFa() {

            }
        });


    }

    public interface GetAllApartmentsAndObserveCallback {
        void onComplete(ArrayList<Apartment> list);
        void onCancel();
    }

    public void getAllApartments(final GetAllApartmentsAndObserveCallback callback){

     modelFirebase.getAllApartmentsAndObserve(new ModelFirebase.GetAllApartmentsAndObserveCallback() {
         public void onComplete(ArrayList<Apartment> list) {

             callback.onComplete(list);

             //TODO later we will bring only the new Apartments.
             //insert it to the SQlite if its exist its will not do nothing but if there is a new Apartment it will add .
             for (int i=0;i<list.size();i++ ){

                 modelSQL.addApartment(modelSQL.getWritableDatabase(),list.get(i));
             }
         }

        @Override
         public void onCancel() {
        callback.onCancel();
        }
     });

    }


    public void getAllApartmentsFromSQL(final GetAllApartmentsAndObserveCallback callback) {


        modelSQL.getAllApartments(modelSQL.getReadableDatabase(), new ModelSQL.ApartmentListenerSQL() {
            @Override
            public void onSQLsucsses(ArrayList<Apartment> apartment) {

                callback.onComplete(apartment);
            }

            @Override
            public void onSQLfail() {

            }
        });
    }



        public interface GetImageListener {

        void onSccess(Bitmap bmp);
        void onFail();

    }





    //id - for the local iamge, we save it as 1.jpg (ID of the apartment + jpg)
    //imageURL for the firebase this is the actually imageURL for download the image

    public void getImage(final String imageURL, final String id, final GetImageListener imageListener) {


                modelSQL.readImageFileFromInternalStorage(MyApplication.getMyContext(), id, ".jpg", new ModelSQL.LocalImageListener() {

                 //onSU interface of ModelSQL > sending it to onSuccess interface of imageListener
                @Override
                public void onSu(Bitmap bmp) {

                    imageListener.onSccess(bmp);
                }


                //onFa interface of ModelSQL
                @Override
                public void onFa() {

                    //we go get them from firebase

                    modelFirebase.getImageFromFireBase(imageURL, new ModelFirebase.GetImageListener() {
                        @Override
                        public void onSccess(Bitmap bmp) {


                            imageListener.onSccess(bmp);



                            //saving the image in the local storage, beacuse its not exist.
                            modelSQL.saveImageToInternalStorage(MyApplication.getMyContext(), bmp, id, ".jpg", new ModelSQL.LocalImageListener() {
                                @Override
                                public void onSu(Bitmap bmp) {
                                    Log.d("TAG8","Image has been saved succecsfully from Firebase to internalStorage");
                                }

                                @Override
                                public void onFa() {


                                    Log.d("TAG8","The image is not exist on InternalStorage and not on Firebase..");
                                }
                            });

                        }

                        @Override
                        public void onFail() {
                            imageListener.onFail();
                        }
                    });

                    //imageListener.onFail();

                }
            });


    }




    //to get the uID of the user that connected
    public interface currentUserCallback{

        public void onConnected(String userID);
        //public void onFails();

    }

    public void getCurrentUser(final currentUserCallback callback){

        modelFirebase.getCurrentUser(new ModelFirebase.currentUsercallback() {
            @Override
            public void onConnected(String userID) {

                callback.onConnected(userID);

            }
        });
    }



    public interface getUserByuIDCallback{

        public void onConnected(User user);

    }



    public void getUserByuID (String user_uID, final getUserByuIDCallback callback){


    modelFirebase.getUserByuID(user_uID, new ModelFirebase.getUserByuIDCallback() {
        @Override
        public void onConnected(User user) {

            callback.onConnected(user);

        }
    });


    }






//SQLite


    //for the big map
    public ArrayList<Apartment> getAllCoordinatesApartments(){

        return modelSQL.getAllCoordinateAparments(modelSQL.getReadableDatabase());

    }


    public interface LoginCallback {


        public void onLoginSu(String msg);
        public void onLoginFa(String msg);

    }


    public void Login (String email, String password, final LoginCallback callback) {

        modelFirebase.Login(email, password, new ModelFirebase.LoginCallback() {
            @Override
            public void onLoginSuccses(String msg) {

               callback.onLoginSu(msg);

            }

            @Override
            public void onLoginFailed(String msg) {
                callback.onLoginFa(msg);
            }
        });
    }



    public interface RegisterCallback {

        public void onRegisterSu();
        public void onRegisterFa();


    }
    public void Register(String email, String password, final RegisterCallback callback) {

        modelFirebase.Register(email, password, new ModelFirebase.RegisterCallback() {
            @Override
            public void onRegisterSu() {
                callback.onRegisterSu();
            }

            @Override
            public void onRegisterFa() {

                callback.onRegisterFa();

            }
        });



    }




}
