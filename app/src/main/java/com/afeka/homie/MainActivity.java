package com.afeka.homie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.afeka.homie.tabs.Tab1;
import com.afeka.homie.tabs.Tab2;
import com.afeka.homie.tabs.Tab3;
import com.afeka.homie.tabs.TabBarFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity implements AparmentListFragment.OnFragmentInteractionListener, OnMapReadyCallback ,AddApartmentFragment.OnFragmentInteractionListener ,DetailsAparmentFragment.OnFragmentInteractionListener,TabBarFragment.OnFragmentInteractionListener , Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener{


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //for the background image
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout_main);
        linearLayout.setBackgroundResource(R.drawable.newyork);



//        AddApartmentFragment wad = new AddApartmentFragment();
//        FragmentTransaction tran = getFragmentManager().beginTransaction();
//        tran.replace(R.id.main_frag_container,wad);
//        tran.commit();

        AparmentListFragment apu = new AparmentListFragment();
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_frag_container,apu);
        tran.commit();


//        Intent intent = new Intent(MainActivity.this,TabBarActivity.class);
//        startActivity(intent);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    //Create the menu bar / action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    //Handiling click events from items in the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add_aparment:

                AddApartmentFragment wad = new AddApartmentFragment();
                FragmentTransaction tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_frag_container, wad);

                //we clean the background
                LinearLayout linear = (LinearLayout) findViewById(R.id.linear_layout_main);
                linear.setBackgroundResource(0);

                tran.addToBackStack(null);

                tran.commit();

                return true;

            case R.id.menu_maps_aparment:

                //MapViewAllFragmen appartments / for the big map
                MapViewAllFragment map = new MapViewAllFragment();
                FragmentTransaction tran1 = getFragmentManager().beginTransaction();
                tran1.replace(R.id.main_frag_container, map);
                tran1.addToBackStack(null);
                tran1.commit();


//            case android.R.id.home:
//
//                Log.d("TAG12","user clicked on home back action");
//                AparmentListFragment apu = new AparmentListFragment();
//                FragmentTransaction tran2 = getFragmentManager().beginTransaction();
//                tran2.replace(R.id.main_frag_container,apu);
//                tran2.commit();
//                return true;

                //case R.id.help:
                //showHelp();
                // return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

