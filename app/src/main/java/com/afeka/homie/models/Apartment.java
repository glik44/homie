package com.afeka.homie.models;

import android.widget.ImageView;

import com.afeka.homie.R;

/**
 * Created by ben on 6/4/17.
 */

public class Apartment {

    public String id;
    public String country;
    public String street;
    public String product_type;
    public String pricemonth;
    public String aparmentURL; //image path of the URLimage ,in file
    //cordinates
    public String xlat;
    public String ylongt;

    public String postedBy; //we will save the uID of the user that posted the Apartment
    public String imageId;

    public Apartment () {


    }
    public Apartment(String id,String country,String street,String product_type,String pricemonth,String xlat,String ylongt,String aparmentURL,String postedBy){

        this.id=id;
        this.country=country;
        this.street=street;
        this.pricemonth=pricemonth;
        this.product_type=product_type;
        this.xlat = xlat;
        this.ylongt = ylongt;
        this.aparmentURL = aparmentURL;
        this.postedBy = postedBy;

    }


    //This c'tor for the big map apartment list
    public Apartment(String id,String country,String street, String xlat,String ylongt){

        this.id = id;
        this.country = country;
        this.street = street;
        this.xlat = xlat;
        this.ylongt = ylongt;

    }
}
