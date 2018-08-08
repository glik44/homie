package com.afeka.homie.tabs;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afeka.homie.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id_tab1";
    private static final String ARG_PARAM2 = "country_tab1";
    private static final String ARG_PARAM3 = "streey_tab1";
    private static final String ARG_PARAM4 = "productType_tab1";
    private static final String ARG_PARAM5 = "price_tab1";
    private static final String ARG_PARAM6 = "latx_tab1";
    private static final String ARG_PARAM7 = "laty_tab1";
    private static final String ARG_PARAM8 = "imageURL_tab1";

    // TODO: Rename and change types of parameters
    private String id_tab1,country_tab1,streey_tab1,productType_tab1,price_tab1,latx_tab1,laty_tab1,imageURL_tab1;
    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2,String param3,String param4,String param5,String param6,String param7,String param8) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_tab1 = getArguments().getString(ARG_PARAM1);
            country_tab1 = getArguments().getString(ARG_PARAM2);
            streey_tab1 = getArguments().getString(ARG_PARAM3);
            productType_tab1 = getArguments().getString(ARG_PARAM4);
            price_tab1 = getArguments().getString(ARG_PARAM5);
            latx_tab1 = getArguments().getString(ARG_PARAM6);
            laty_tab1 = getArguments().getString(ARG_PARAM7);
            imageURL_tab1 = getArguments().getString(ARG_PARAM8);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        TextView idTab1 = (TextView) view.findViewById(R.id.id_tab_details);
        TextView countryTab1 = (TextView) view.findViewById(R.id.country_tab_details);
        TextView streetTab1 = (TextView) view.findViewById(R.id.street_tab_details);
        TextView productTypeTab1 = (TextView) view.findViewById(R.id.prodcutType_tab_details);
        TextView priceTab1 = (TextView) view.findViewById(R.id.price_tab_details);
        TextView latxTab1 = (TextView) view.findViewById(R.id.latx_tab_details);
        TextView latyTab1 = (TextView) view.findViewById(R.id.laty_tab_details);
        TextView imgViewURLTab1 = (TextView) view.findViewById(R.id.imgURL_tab_details);


        idTab1.setText(id_tab1);
        countryTab1.setText(country_tab1);
        streetTab1.setText(streey_tab1);
        productTypeTab1.setText(productType_tab1);
        priceTab1.setText(price_tab1);
        latxTab1.setText(latx_tab1);
        latyTab1.setText(laty_tab1);
        imgViewURLTab1.setText(imageURL_tab1);


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
