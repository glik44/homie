package com.afeka.homie.models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.afeka.homie.LoginActivity;
import com.afeka.homie.MainActivity;
import com.afeka.homie.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by ben on 6/10/17.
 */

        public class ModelFirebase {

            private FirebaseAuth mAuth;

            ModelFirebase() {

                mAuth = FirebaseAuth.getInstance();

            }

            public void addApartmentToFireBase(Apartment at) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("apartments");
                myRef.child(at.id).setValue(at);
                Log.d("TAG", "The Apartment has been uploaded to FireBase");
            }


            public void addUserToFireBase(User user) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.child(user.uID).setValue(user);

            }


            interface GetAllApartmentsAndObserveCallback {
                void onComplete(ArrayList<Apartment> list);

        void onCancel();
    }


    public void getAllApartmentsAndObserve(final GetAllApartmentsAndObserveCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("apartments");
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Apartment> list = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Apartment apartment = snap.getValue(Apartment.class);
                    list.add(apartment);
                }

                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


                callback.onCancel();
            }


        });
    }


    interface FirebaseStorageimageURL {
        public void onSuccess(Uri downloadUrl);

        public void onFailed();

    }

    FirebaseStorageimageURL firebasestorage;

    //Saving Image to Firebase storage
    public void saveImageToFireBase(Bitmap imageBmp, String id, final FirebaseStorageimageURL firebasestorage) {
         FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                firebasestorage.onFailed();
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("TAG", downloadUri.toString());
                    firebasestorage.onSuccess(downloadUri);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });


    }

    interface GetImageListener {

        void onSccess(Bitmap bmp);

        void onFail();

    }

    public void getImageFromFireBase(String url, final GetImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(3 * ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onSccess(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG", exception.getMessage());
                listener.onFail();
            }
        });
    }


    interface currentUsercallback {

        public void onConnected(String userID);
        //public void onFails();

    }


    //we must get the currentUser so that we can search for his uID in our table in firebase
    //this only return the uiD of the current user not the full user
//getting the currect user who logged in
    public void getCurrentUser(final currentUsercallback cuserCallback) {

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        String userID = currentUser.getUid().toString();
        cuserCallback.onConnected(userID);

    }

    interface getUserByuIDCallback {
        public void onConnected(User user);
    }

    /**
     * @param usera this is the String uID  we got to search for (for tab3) and we return the details
     * @param callback we return the spesific user by callback
     */
    //search for spesific user by its uID (for tab3) so we can get the details about who postedBy
    public void getUserByuID(final String usera, final getUserByuIDCallback callback) {


        //we search the user in the Users table until we find a match
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                    User user = userSnapshot.getValue(User.class);
                    if (usera.equals(user.uID)) {

                        callback.onConnected(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getAllUsers() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("TAG12", "" + snapshot.getChildrenCount());

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                    User user = userSnapshot.getValue(User.class);
                    Log.d("TAG12", user.name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public interface LoginCallback {

        public void onLoginSuccses(String msg);

        public void onLoginFailed(String msg);

    }

    public void Login(String email, String password, final LoginCallback callback) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    //if we want all the details about the user we need to use our function getCurrentUser, this gives only limited info that saved on the firebase ref (such as uid)
                    if (user != null) {
                        Log.d("TAG15", "user email:" + " " + user.getEmail() + "user uID:" + user.getUid());
                    }

                    callback.onLoginSuccses("connectedUserWithEmail:success");


                } else {

                    callback.onLoginFailed("connectedUserWithEmail:failed");

                }

            }
        });
    }


    interface RegisterCallback {

        public void onRegisterSu();

        public void onRegisterFa();


    }

    public void Register(String email, String password, final RegisterCallback callback) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    callback.onRegisterSu();
                } else {

                    callback.onRegisterFa();
                }


            }
        });
    }

}



