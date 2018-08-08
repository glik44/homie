package com.afeka.homie;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afeka.homie.models.Apartment;
import com.afeka.homie.models.Model;
import com.afeka.homie.models.ModelMem;
import com.afeka.homie.models.ModelSQL;
import com.afeka.homie.models.User;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AparmentListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AparmentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AparmentListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "list_id";
    private static final String ARG_PARAM2 = "list_country";
    private static final String ARG_PARAM3 = "list_street";
    private static final String ARG_PARAM4 = "list_productType";
    private static final String ARG_PARAM5 = "list_price";
   // private static final String ARG_PARAM6 = "list_country";
   ProgressBar pb;

    ArrayList<Apartment> datapr = new ArrayList<>();


    private String id_mParam,country_mParam,street_mParam,productType_mParam,price_mParam;

    private OnFragmentInteractionListener mListener;

    public AparmentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AparmentListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AparmentListFragment newInstance(String param1, String param2) {
        AparmentListFragment fragment = new AparmentListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_mParam = getArguments().getString(ARG_PARAM1);
            country_mParam = getArguments().getString(ARG_PARAM2);
            street_mParam = getArguments().getString(ARG_PARAM3);
            productType_mParam = getArguments().getString(ARG_PARAM4);
            price_mParam = getArguments().getString(ARG_PARAM5);
        }
    }


//    interface GetAllApartmentsAndObserveCallback {
//        void onComplete(ArrayList<Apartment> list);
//        void onCancel();
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Model.instance.getCurrentUser(new Model.currentUserCallback() {
//            @Override
//            public void onConnected(String userID) {
//
//                Log.d("TAG12","The Connected user is: " + currentUser.name + " " + currentUser.uID + " " );
//
//            }
//        });

        final baseAparmentAdapter adapter = new baseAparmentAdapter();
       // ModelMem ma = new ModelMem();


        // Model.instance(new ModelMem());
        Model.instance.getAllApartments(new Model.GetAllApartmentsAndObserveCallback() {
            @Override
            public void onComplete(ArrayList<Apartment> list) {

                Log.d("TAG","Going the FB on Start again..");
                datapr = list;
                //pb.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancel() {


            }
        });

        // Inflate the layout for this fragment
        View converView =  inflater.inflate(R.layout.fragment_aparment_list, container, false);
        ListView listView = (ListView) converView.findViewById(R.id.aparment_list_items);


        ProgressBar pb = (ProgressBar) converView.findViewById(R.id.progress_bar_list);

       // pb.setVisibility(View.VISIBLE);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("TAG", "press"+ " + " + position);

                //getting the right aparment and getting the info before showing it
                Apartment apr = datapr.get(position);

                String idr = apr.id;
                String county = apr.country;
                String street = apr.street;
                String product_type = apr.product_type;
                String price_month = apr.pricemonth;
                String lat = apr.xlat;
                String longt = apr.ylongt;
                String aparURL = apr.aparmentURL;
                String postedBy = apr.postedBy;

                DetailsAparmentFragment dp = new DetailsAparmentFragment();

                //seding the argu bundle and getting fragment back using detailsapartmentfragment fragment func
                dp = dp.newInstance(idr,county,street,product_type,price_month,lat,longt,aparURL,postedBy);

                FragmentTransaction tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_frag_container,dp);
                tran.addToBackStack(null);
                tran.commit();

    }


});

        return converView;
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


    //onPrepareOptionMenu
    MenuItem menuItem;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menuItem= menu.findItem(R.id.menu_save_aparment);
        menuItem.setVisible(false);


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


    public class baseAparmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datapr.size();

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

          if (convertView == null){

              LayoutInflater inflater = LayoutInflater.from(MyApplication.getMyContext());

              convertView = inflater.inflate(R.layout.aparment_list_row, parent, false);


           //   pb = (ProgressBar) convertView.findViewById(R.id.progress_bar);

          }

             TextView id = (TextView) convertView.findViewById(R.id.ap_row_id);
             TextView country = (TextView) convertView.findViewById(R.id.ap_row_country);
             TextView price = (TextView) convertView.findViewById(R.id.ap_row_price);
             final ImageView imgView = (ImageView) convertView.findViewById(R.id.ap_row_image);


            final ImageView imgViewUser = (ImageView) convertView.findViewById(R.id.ap_row_imageUser);


           final Apartment apartment = datapr.get(position);

            id.setText(apartment.id);
            country.setText(apartment.country);
            price.setText(apartment.pricemonth);

            imgView.setTag(apartment.aparmentURL);


            if (!apartment.aparmentURL.isEmpty()){

                //GOT THE IMAGE from FB or Local apartmentURL for Firebase and apartment id for Local DB
                Model.instance.getImage(apartment.aparmentURL,apartment.id, new Model.GetImageListener() {

                    @Override
                    public void onSccess(Bitmap bmp) {

                        String urlIMG = imgView.getTag().toString();
                        if (urlIMG.equals(apartment.aparmentURL)) {
                            //String imUrl = imgiew.getTag().toString();

                            imgView.setImageBitmap(bmp);

                        } else {

                            Log.d("TAG", "error");
                        }

                    }
                    @Override
                    public void onFail() {
                    }
                });

                //Getting the image for the User, first we search his User
               Model.instance.getUserByuID(apartment.postedBy, new Model.getUserByuIDCallback() {
                   @Override
                   public void onConnected(User user) {

                       //This for the image_user_posted , userImg_URL for firebase , and user.UID its for local db, we search in both so we need to give this info. local db save it as uID.jpg and firebase save it as Storage as the full name and link a image to it
                        Model.instance.getImage(user.userImg_URL, user.uID, new Model.GetImageListener() {
                            @Override
                            public void onSccess(Bitmap bmp) {

                                imgViewUser.setImageBitmap(bmp);
                            }

                            @Override
                            public void onFail() {

                            }
                        });

                   }
                     });
              }
            return convertView;
        }
    }

}
