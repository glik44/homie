package com.afeka.homie;


import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment extends Fragment {

    public static boolean showButtonMap = true;

    public static String ARG_PARAM1 = "latx";
    public static String ARG_PARAM2 = "laty";
    public static String ARG_PARAM3 = "ap2";

    public static String lattiude = "32.074704"; //default
    public static String longttiude = "34.831282";   //default
    public static String imgDrawableid ="ap2";

    MapView mMapView;
    private GoogleMap googleMap;


    EditText result;
    EditText lat;
    EditText longt;
    String apartment_id;

    //String lattiude,longttiude;
    AlertDialog dialog;
    public MapViewFragment() {
        // Required empty public constructor
    }



//    //resizeMapIcons for the GoogleMap Markers
//    public Bitmap resizeMapIcons(String iconName,int width, int height){
//        Bitmap ib;
//        if ( (ib = ModelSQL.readImageFileFromInternalStorage(MyApplication.getMyContext(),iconName,".jpg")) == null) {
//
//            ib = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("ap2", "drawable","com.example.ben.codlic"));;
//        }
//       // Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable","com.example.ben.codlic"));
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(ib, width, height, false);
//        return resizedBitmap;
//    }




    //param1 = latx,
    //param2 = laty;
    //param3 = R.draweable;
    public static MapViewFragment newInstanceCoordinates(String param1, String param2,String param3) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3,param3);
        fragment.setArguments(args);
        return fragment;
    }
    ArrayList dataA;
    ArrayList<Address> dataB;
    String location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lattiude = getArguments().getString(ARG_PARAM1);
            longttiude = getArguments().getString(ARG_PARAM2);
            //imgDrawableid = getArguments().getString(ARG_PARAM3);
            apartment_id = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_map_view, container, false);


        Button addCoordinate = (Button) rootView.findViewById(R.id.add_coordinate);


        if (!showButtonMap) {

            addCoordinate.setVisibility(View.INVISIBLE);
        }

        //Dialog for coordinate
        addCoordinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","mapviewFragment: Btn has been clicked!");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

                LayoutInflater li = LayoutInflater.from(getActivity());
                View myview = li.inflate(R.layout.dialog_coordinate,null);

                Button coorindateSaveBtn = (Button) myview.findViewById(R.id.save_coorindate);

                lat = (EditText) myview.findViewById(R.id.lat);
                longt = (EditText) myview.findViewById(R.id.longt);

                result = (EditText) myview.findViewById(R.id.dialog_edit_text);


                mBuilder.setView(myview);
                dialog =mBuilder.create();
                dialog.show();


                coorindateSaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG","cooridnateSaveBtn has been clicked!");

//
//                        Geocoder geocoder = new Geocoder(getActivity());
//
//                        location = result.getText().toString();
//
//                        try {
//                            dataB = (ArrayList<Address>) geocoder.getFromLocationName(location,1);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        Address adresses =  dataB.get(0);
//                        double lata = adresses.getLatitude();
//                        double longta = adresses.getLongitude();
//                        LatLng  latLng= new LatLng(lata,longta);
//                        googleMap.addMarker((new MarkerOptions().position(latLng).title("Marker")));
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


                        lattiude = lat.getText().toString();
                        longttiude = longt.getText().toString();

                        dialog.hide();

                        mMapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap mMap) {
                                googleMap = mMap;



                               // googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));

                                LatLng sydney = new LatLng(Float.parseFloat(lattiude),Float.parseFloat(longttiude));

                                googleMap.addMarker((new MarkerOptions().position(sydney)));

                                //  default sydent -34,151
                              //  googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                             //  googleMap.addMarker((new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(apartment_id,125,125)))));
                              //  googleMap.addMarker((new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromBitmap(SecondCircle(apartment_id)))));

                                //For zooming automatically to the location of the marker
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }
                        });

                    }
                });



            }
        });



        mMapView = (MapView) rootView.findViewById(R.id.mapView);
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


                // Customise the styling of the base map using a JSON object defined in raw/style_json
                // in a string resource file. First create a MapStyleOptions object
                // from the JSON styles string, then pass this to the setMapStyle method of the GoogleMap object

               // googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));

                // For showing a move to my location button
              //  googleMap.setMyLocationEnabled(true);

                //Ramat Gan 32.074704,34.831282
                // For dropping a marker at a point on the Map

                LatLng sydney; //-34,151

              //  String lattiude = lat.getText().toString();
              //  String longttiude = longt.getText().toString();

               if ((lattiude == null)  && (longttiude== null)){

                   sydney = new LatLng(32.074704, 34.831282);

               }else {

                   sydney = new LatLng(Float.parseFloat(lattiude),Float.parseFloat(longttiude));
               }

                //default sydent -34,151
             //  googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                if (apartment_id == null){
                    apartment_id = "1";
                }

                googleMap.addMarker((new MarkerOptions().position(sydney)));
              //  googleMap.addMarker((new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon))));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

}
