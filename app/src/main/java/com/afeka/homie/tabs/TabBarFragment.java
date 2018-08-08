package com.afeka.homie.tabs;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afeka.homie.MyApplication;
import com.afeka.homie.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabBarFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "country";
    private static final String ARG_PARAM3 = "street";
    private static final String ARG_PARAM4 = "product_type";
    private static final String ARG_PARAM5 = "price_per_month";
    private static final String ARG_PARAM6 = "latx";
    private static final String ARG_PARAM7 = "laty";
    private static final String ARG_PARAM8 = "imgURL";
    private static final String ARG_PARAM9 = "postedBy";

    public static String ARG_DRAWABLE_ID = "ap0";

    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    // TODO: Rename and change types of parameters
    private String id_tab_param;
    private String country_tab_param,street_tab_param,productType_tab_param,price_tab_param,latx_tab_param,laty_tab_param,imgURL_tab_param,postedBy_tab_param;

    Fragment tabs[] = new Fragment[3] ;

    private OnFragmentInteractionListener mListener;

    public TabBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabBarFragment newInstance(String param1, String param2,String param3,String param4,String param5,String param6,String param7,String param8,String param9) {
        TabBarFragment fragment = new TabBarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        args.putString(ARG_PARAM9,param9);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_tab_param = getArguments().getString(ARG_PARAM1);
            country_tab_param = getArguments().getString(ARG_PARAM2);
            street_tab_param = getArguments().getString(ARG_PARAM3);
            productType_tab_param = getArguments().getString(ARG_PARAM4);
            price_tab_param = getArguments().getString(ARG_PARAM5);
            latx_tab_param = getArguments().getString(ARG_PARAM6);
            laty_tab_param= getArguments().getString(ARG_PARAM7);
            imgURL_tab_param = getArguments().getString(ARG_PARAM8);
            postedBy_tab_param = getArguments().getString(ARG_PARAM9);

        }
    }
    Bundle savedInstanceState;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_bar, container, false);

        final Button tab1 = (Button) view.findViewById(R.id.tab1);
        Button tab2 = (Button) view.findViewById(R.id.tab2);
        Button tab3 = (Button) view.findViewById(R.id.tab3);


        Tab1 taba1 = new Tab1();
        taba1 = taba1.newInstance(id_tab_param,country_tab_param,street_tab_param,productType_tab_param,price_tab_param,latx_tab_param,laty_tab_param,imgURL_tab_param);
        tabs[0] = taba1;

        Tab2 taba2 = new Tab2();
        taba2 = taba2.newInstance(latx_tab_param,laty_tab_param,id_tab_param);

        tabs[1] = taba2;

        Tab3 taba3 = new Tab3();
        taba3 = taba3.newInstance(postedBy_tab_param);
        tabs[2] = taba3;

        tab1.setOnClickListener(this);
        tab1.setTag(0);

        tab2.setOnClickListener(this);
        tab2.setTag(1);

        tab3.setOnClickListener(this);
        tab3.setTag(2);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int key = (int)v.getTag();
        Log.d("TAG", String.valueOf(key));

        switch(key) {

            case 0:

                FragmentTransaction tran1 = getFragmentManager().beginTransaction();
                tran1.replace(R.id.container_tabar_fragment, tabs[key]);
                tran1.commit();

            case 1:
                FragmentTransaction tran2 = getFragmentManager().beginTransaction();
                tran2.replace(R.id.container_tabar_fragment,tabs[key]);
                tran2.commit();


            case 2:

                FragmentTransaction tran3 = getFragmentManager().beginTransaction();
                tran3.replace(R.id.container_tabar_fragment,tabs[key]);
                tran3.commit();

//                Intent intent = new Intent(MyApplication.getMyContext(), Tab4.class);
//
//
//                startActivity(intent);

            default:
            }

        }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
