package com.jlabs.peepaid.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlabs.peepaid.R;
import com.jlabs.peepaid.customcomponents.ButtonModarno;
import com.jlabs.peepaid.customcomponents.TextViewModernM;
import com.jlabs.peepaid.model.Image;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;


public class SlideshowDialogFragment extends DialogFragment implements LocationListener {
    private GoogleMap googleMap;
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Image> images;
    private Image image;
    private ViewPager viewPager;
    private ButtonModarno here, write_re;
    TextViewModernM full_add;
    Image ing;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblTitle, lblDate;
    private int selectedPosition = 0;
    private static View v;
    LocationManager locationManager;
    LocationListener locationListener;
    ProgressDialog mProgressDialog;
    double latte, longi;
    LinearLayout mLayout;
    TextView fragment;

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ing=new Image();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Retrieving Current Location...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        locationManager = (LocationManager)
                getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        locationListener = this;

        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }

        try {
            v = inflater.inflate(R.layout.detailed_toilet, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //View v = inflater.inflate(R.layout.detailed_toilet, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        here = (ButtonModarno) v.findViewById(R.id.her_i_m);
        write_re = (ButtonModarno) v.findViewById(R.id.write_re);
        full_add = (TextViewModernM) v.findViewById(R.id.full_add);
        mLayout = (LinearLayout) v.findViewById(R.id.mLayout);


        fragment = (TextView) v.findViewById(R.id.fragment);
//        lblDate = (TextView) v.findViewById(R.id.date);

        images = (ArrayList<Image>) getArguments().getSerializable("images");
        image = (Image) getArguments().getSerializable("image");
        for(int i=0;i<images.size();i++)
        {
            Log.i("sdjlks"," sds "+images.get(i).getLarge());
        }
        //selectedPosition = getArguments().getInt("position");
        selectedPosition = 0;

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        InkPageIndicator inkPageIndicator = (InkPageIndicator) v.findViewById(R.id.indicator);
        inkPageIndicator.setViewPager(viewPager);
        setCurrentItem(selectedPosition);


        fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapActivity.class);
                i.putExtra("lat", image.getLat());
                i.putExtra("long", image.getLo());
                startActivity(i);
            }
        });

        here.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                useCurrent();


            }
        });
        write_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.write_review);
                // set the custom dialog components - text, image and button
                ButtonModarno submit =(ButtonModarno)dialog.findViewById(R.id.submit);
                // if button is clicked, close the custom dialog
                final EditText mEditText = (EditText) dialog.findViewById(R.id.edittext);
                TextViewModernM textView = new TextViewModernM(getActivity());
                textView.setText("Kashyap");


                submit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mLayout.addView(createNewTextView(mEditText.getText().toString()));
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });



        CreateMap();

        return v;
    }

    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextViewModernM textView = new TextViewModernM(getActivity());
        textView.setLayoutParams(lparams);
        textView.setText("New text: " + text);
        return textView;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {


    //    Image image = images.get(position);
    //    Log.i("Myapp","Ok "+image.getLarge());
//        lblTitle.setText(image.getName());
//        lblDate.setText(image.getTimestamp());
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = (Fragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

            Image image = images.get(position);

            Glide.with(getActivity()).load(image.getMedium())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }




    //For Map


    public  void CreateMap()
    {


        try {
            // Loading map
            initilizeMap();
            ing = new Image();
            // Changing map type
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);

            googleMap.getUiSettings().setZoomControlsEnabled(false);

            googleMap.getUiSettings().setMyLocationButtonEnabled(false);

            googleMap.getUiSettings().setCompassEnabled(true);

            // Enable / Disable Rotate gesture
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            // Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled(true);


            double latitude ;
            double longitude ;

               latitude=image.getLat();
                longitude=image.getLo();
                full_add.setText(image.getTitle());

            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latitude, longitude))
                    .title(" Maps ");

            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


            googleMap.addMarker(marker);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude,
                            longitude)).zoom(15).build();

            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initilizeMap() {
        if (googleMap == null) {

            googleMap = ((SupportMapFragment)this.getFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Myapp ", "Hello Ondestroy");
            this.getFragmentManager().beginTransaction().remove(this.getFragmentManager().findFragmentById(R.id.map));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Myapp ", "Hello OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Myapp ", "Hello OnResume");
    }





    //New Location//
    public void useCurrent(){
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            mProgressDialog.show();
//                    locationManager.requestLocationUpdates(
//                            LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                askForPermission();
            else
                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }




    @Override
    public void onLocationChanged(Location loc) {
        Toast.makeText(
                getActivity().getBaseContext(),
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        latte =  loc.getLatitude();
        longi = loc.getLongitude();
        Log.i("Longit" + longi,""+latte);
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.new_activate_deal);

        Location loc1 = new Location("");
        loc1.setLatitude(latte);
        loc1.setLongitude(longi);

        Location loc2 = new Location("");
        loc2.setLatitude(image.getLat());
        loc2.setLongitude(image.getLo());

       final float distanceInMeters = loc1.distanceTo(loc2);
        Log.i("DATAAA", ""+distanceInMeters);
        // set the custom dialog components - text, image and button


        final LinearLayout redeam=(LinearLayout)dialog.findViewById(R.id.first);
        final RelativeLayout not_there=(RelativeLayout)dialog.findViewById(R.id.not_there);
        final LinearLayout rewarded=(LinearLayout)dialog.findViewById(R.id.rewarded);
        final RippleBackground rippleBackground=(RippleBackground)dialog.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        ButtonModarno dialogButton =(ButtonModarno)dialog.findViewById(R.id.activate);
        // if button is clicked, close the custom dialog
        if (distanceInMeters<500){
            dialogButton.setBackgroundColor(Color.parseColor("#FF784B"));
        }



        dialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (distanceInMeters < 500) {
                    rewarded.setVisibility(View.VISIBLE);
                    redeam.setVisibility(View.GONE);
                }
                else {
                    not_there.setVisibility(View.VISIBLE);
                    not_there.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), MapActivity.class);
                            i.putExtra("lat", image.getLat());
                            i.putExtra("long", image.getLo());
                            startActivity(i);


                            dialog.dismiss();
                        }
                    });
                }

            }
        });

        dialog.show();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void askForPermission()
    {
        if (getActivity().checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 998);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);

    }
    //New Location//




}
