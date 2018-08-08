package com.afeka.homie.models;

import java.util.ArrayList;

/**
 * Created by ben on 6/9/17.
 */

public class ModelMem  {


    public static ArrayList<Apartment> data = new ArrayList<Apartment>();


    public void init (){

        for (int i=0;i<5;i++){

            data.add(new Apartment("" + i,"L.A,Rimonim " + i,"Rimonim 78","House","20" + i + "$","32.074","34.831","ap" + i ,"1234"));


        }

    }

    public  void addApartment (Apartment apartment) {

        data.add(apartment);

    }

    public ArrayList<Apartment> getAllApartments(){

        return data;
    }

}