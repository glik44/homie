package com.afeka.homie;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afeka.homie.models.Model;
import com.afeka.homie.models.ModelSQL;
import com.afeka.homie.models.User;
import com.afeka.homie.tabs.Tab1;
import com.afeka.homie.tabs.TabBarFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsAparmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsAparmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsAparmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1"; //ID
    private static final String ARG_PARAM2 = "param2"; //country
    private static final String ARG_PARAM3 = "param3"; //street
    private static final String ARG_PARAM4 = "param4"; //product_type
    private static final String ARG_PARAM5 = "param5"; //price_per_month
    private static final String ARG_PARAM6 = "param6"; // latx
    private static final String ARG_PARAM7 = "param7"; // lat y
    private static final String ARG_PARAM8 = "param8"; //aparURL
    private static final String ARG_PARAM9 ="param9"; //postedByuID we save the userUid that posted the apartment



    // TODO: Rename and change types of parameters
    private String id_param;
    private String country_param,street_param,pt_param,price_param,latx_param,laty_param,imageURL_param,postedByuID_param;

    private OnFragmentInteractionListener mListener;

    public DetailsAparmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsAparmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsAparmentFragment newInstance(String param1, String param2,String param3,String param4,String param5,String param6,String param7,String param8,String param9) {
        DetailsAparmentFragment fragment = new DetailsAparmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3,param3);
        args.putString(ARG_PARAM4,param4);
        args.putString(ARG_PARAM5,param5);
        args.putString(ARG_PARAM6,param6);
        args.putString(ARG_PARAM7,param7);
        args.putString(ARG_PARAM8,param8);
        args.putString(ARG_PARAM9,param9);
        fragment.setArguments(args);
        return fragment;
    }


    //cathing the argu that arrived with the frag extricate from the bundle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_param = getArguments().getString(ARG_PARAM1); //ID
            country_param = getArguments().getString(ARG_PARAM2); //country
            street_param = getArguments().getString(ARG_PARAM3); //street
            pt_param = getArguments().getString(ARG_PARAM4); // product type
            price_param = getArguments().getString(ARG_PARAM5); // price
            latx_param = getArguments().getString(ARG_PARAM6); //lat x
            laty_param = getArguments().getString(ARG_PARAM7); //laty
            imageURL_param = getArguments().getString(ARG_PARAM8);//imageURL
            postedByuID_param = getArguments().getString(ARG_PARAM9); //postedBy this is the uID of the user that posted the apartment

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_aparment, container, false);

//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);


        TextView country = (TextView) view.findViewById(R.id.details_country);
        TextView street = (TextView) view.findViewById(R.id.details_street);
        TextView price = (TextView) view.findViewById(R.id.details_price);
        final ImageView img = (ImageView) view.findViewById(R.id.details_photo);

        final ImageView img_user_post = (ImageView) view.findViewById(R.id.details_image_user);


        //for the image of apartment
        Model.instance.getImage(imageURL_param,id_param, new Model.GetImageListener() {
            @Override
            public void onSccess(Bitmap bmp) {

                img.setImageBitmap(bmp);


                //for the userImage URL to put in the getImage func below we need search the user first and get all the details


            }

            @Override
            public void onFail() {

            }
        });


       Model.instance.getUserByuID(postedByuID_param, new Model.getUserByuIDCallback() {
           @Override
           public void onConnected(User user) {


               Model.instance.getImage(user.userImg_URL, user.uID, new Model.GetImageListener() {
                   @Override
                   public void onSccess(Bitmap bmp) {

                       img_user_post.setImageBitmap(bmp);
                   }

                   @Override
                   public void onFail() {

                   }
               });
           }
       });

        country.setText(country_param);
        street.setText(street_param);
        price.setText(price_param);




        TabBarFragment tab = new TabBarFragment();

        tab = tab.newInstance(id_param,country_param,street_param,pt_param,price_param,latx_param,laty_param,imageURL_param,postedByuID_param);

        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.second_frag_container,tab);

        tran.commit();


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
