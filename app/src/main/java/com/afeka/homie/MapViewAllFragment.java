package com.afeka.homie;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afeka.homie.models.Apartment;
import com.afeka.homie.models.Model;
import com.afeka.homie.models.ModelSQL;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;



//BIG MAP Fragment, Showing all the Apartments on the map

public class MapViewAllFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static String lattiude;
    public static String longttiude;
    String apartment_id;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap googleMap;
    Apartment apr;
    double lati,longLat;

    ArrayList<Apartment> list;


    public MapViewAllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapViewAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapViewAllFragment newInstance(String param1, String param2) {
        MapViewAllFragment fragment = new MapViewAllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //this is calling to onPrepareOptionsMenu()
        setHasOptionsMenu(true);

        list = Model.instance.getAllCoordinatesApartments();

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    MenuItem menuItem;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menuItem = menu.findItem(R.id.menu_save_aparment);
        menuItem.setVisible(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("TAG10","MapViewAllFragment: onCreateview");
        View rootView = inflater.inflate(R.layout.fragment_map_view_all, container, false);


        MapView mMapView = (MapView) rootView.findViewById(R.id.mapView_all);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                 googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));

                Log.d("TAG10","onMapReady:");

                Log.d("TAG10", String.valueOf(list.size()) + "" +" apartments found");

                //getting all the coordinates for the big map
                for (int i = 0; i < list.size(); i++) {
                      lati=Double.parseDouble(list.get(i).xlat);
                      longLat=Double.parseDouble(list.get(i).ylongt);
                    //googleMap.addMarker(new MarkerOptions().position(new LatLng(lati,longLat)).title(list.get(i).country).snippet(list.get(i).street));
                   // googleMap.addMarker((new MarkerOptions().position(new LatLng(lati,longLat)).title(list.get(i).country).snippet(list.get(i).street)).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(list.get(i).id,110,110))));


                    apr = list.get(i);

                    Model.instance.getImage(apr.aparmentURL, apr.id, new Model.GetImageListener() {
                        @Override
                        public void onSccess(Bitmap bmp) {

                            //resizeMapIcons for the GoogleMap Markers
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, 100, 100, false);

                            googleMap.addMarker((new MarkerOptions().position(new LatLng(Double.parseDouble(apr.xlat),Double.parseDouble(apr.ylongt))).title(apr.country).snippet(apr.street)).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));

                        }

                        @Override
                        public void onFail() {

                        }
                    });

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(32.074704,34.831282)).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                }


                //  googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                if (apartment_id == null){
                    apartment_id = "1";
                }


               // googleMap.addMarker((new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(apartment_id,125,125)))));

                // For zooming automatically to the location of the marker

            }
        });

        return rootView;
    }



    }

