package com.afeka.homie;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afeka.homie.models.Apartment;
import com.afeka.homie.models.Model;
import com.afeka.homie.models.ModelFirebase;
import com.afeka.homie.models.ModelSQL;
import com.afeka.homie.models.User;
import com.google.android.gms.maps.MapView;

import java.net.URL;

import static com.afeka.homie.Main2ActivityImage.RESULT_SUCSSES;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddApartmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddApartmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AddApartmentFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "country";
    private static final String ARG_PARAM3 = "street";
    private static final String ARG_PARAM4 = "productType";
    private static final String ARG_PARAM5 = "price";
    private static final String ARG_PARAM6 = "latx";
    private static final String ARG_PARAM7 = "laty";
    private static final String ARG_PARAM8 = "imgviewURL";

      static final int REQUEST_ADD_ID = 1;

    ImageView imgview;
    Bitmap bmp;
    String image_URL;

    String latx,laty;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText apr_id,apr_street, apr_country,apr_productType,apr_price;

    private OnFragmentInteractionListener mListener;

    public AddApartmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddApartmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddApartmentFragment newInstance(String param1, String param2) {
        AddApartmentFragment fragment = new AddApartmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View contentView =  inflater.inflate(R.layout.fragment_add_apartment, container, false);


        //set the back arrow on the action bar
      //  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

         apr_id = (EditText) contentView.findViewById(R.id.addapartment_id);
         apr_street = (EditText) contentView.findViewById(R.id.addapartment_street);
         apr_country = (EditText) contentView.findViewById(R.id.addapartment_country);
         apr_productType = (EditText) contentView.findViewById(R.id.addapartment_productType);
         apr_price = (EditText) contentView.findViewById(R.id.addapartment_price);


        MapViewFragment mvf = new MapViewFragment();

       // mvf = mvf.newInstanceCoordinates()

        MapViewFragment.showButtonMap = true;
        FragmentTransaction trana = getFragmentManager().beginTransaction();
        trana.add(R.id.second_frag_container, mvf);
       // trana.addToBackStack(null);
        trana.commit();



        imgview = (ImageView) contentView.findViewById(R.id.addapartment_imgView);

        Button btnImage = (Button) contentView.findViewById(R.id.add_image);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),Main2ActivityImage.class);
                startActivityForResult(intent,REQUEST_ADD_ID);

            }
        });


        return contentView;
    }


    //we catch it from Main2ActivityImage, setResult()
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == REQUEST_ADD_ID) && (data !=null )) {

            Log.d("TAG","success for onActivityResult");


            //uncompress the image
            byte[] bytes = data.getByteArrayExtra("bitmapImage");
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imgview.setImageBitmap(bmp);
            imgview.setVisibility(View.VISIBLE);

        }
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

        //this is calling to onPrepareOptionsMenu()
        setHasOptionsMenu(true);
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



    //onPrepareOptionMenu
    MenuItem menuItem;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menuItem = menu.findItem(R.id.menu_add_aparment);
        menuItem.setVisible(false);

        menuItem= menu.findItem(R.id.menu_save_aparment);

        //Action bar Saving button save_aparment
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                if (apr_id.getText().toString().matches("") || apr_country.getText().toString().matches("") || apr_street.getText().toString().matches("")) {

                    Toast.makeText(MyApplication.getMyContext(), "Dont leave empty fields!", Toast.LENGTH_SHORT).show();

                } else {

                Log.d("TAG","Need to put the Actionsbar handlers listeners in onPrepareOptionMenu");


                //get the cooridnates
                final String latx = MapViewFragment.lattiude;
                final String laty = MapViewFragment.longttiude;


                //the user didnt provide a image for apartment, we put default image
                if (bmp == null) {
                     bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ap2);
                }

                //Saving the Images with the unique ID as 1.jpg will be aparment 1 etc.

                //This is save the image both FireBase and SQlite , firebase asyn so we wait until we get the URL , then we save at SQLite
                Model.instance.saveImage(bmp, apr_id.getText().toString(), new Model.FirebaseStorageimageURL() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {
                        //IMAGE_URL the url of the saved image in FB
                        image_URL = downloadUrl.toString();

                        //we get the userID
                        Model.instance.getCurrentUser(new Model.currentUserCallback() {
                            @Override
                            public void onConnected(String userID) {

                                //we search the userID to get all the details
                                Model.instance.getUserByuID(userID, new Model.getUserByuIDCallback() {
                                    @Override
                                    public void onConnected(User user) {

                                        Model.instance.addApartment(new Apartment(apr_id.getText().toString(),apr_country.getText().toString(),apr_street.getText().toString(),apr_productType.getText().toString(),apr_price.getText().toString(),latx ,laty,image_URL,user.uID));


                                    }
                                });


                            }
                        });


                    }

                    @Override
                    public void onFailed() {

                    }
                });


                AparmentListFragment alf = new AparmentListFragment();

                FragmentTransaction tran = getFragmentManager().beginTransaction();
                // tran.addToBackStack(null);
                tran.replace(R.id.main_frag_container,alf);
                tran.commit();

            }

            return false;

            }

        });


    }


}
