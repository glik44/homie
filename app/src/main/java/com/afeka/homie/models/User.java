package com.afeka.homie.models;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

/**
 * Created by ben on 6/16/17.
 */

public class User {

    public String name;
    public String email;
    public String password;
    public String userImg_URL;
    public String uID;



    public User (){

    }

public User (String name,String email,String password,String userImg_URL,String uID){

    this.name = name;
    this.email = email;
    this.password = password;
    this.userImg_URL = userImg_URL;
    this.uID = uID;

}


   // To get the profile information retrieved from the sign-in providers linked to a user, use the getProviderData method. For example:



}

