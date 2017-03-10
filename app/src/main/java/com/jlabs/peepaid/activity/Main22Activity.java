package com.jlabs.peepaid.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jlabs.peepaid.R;
import com.jlabs.peepaid.Rounded.RoundedImageView;
import com.jlabs.peepaid.customcomponents.TextViewModernM;
import com.jlabs.peepaid.customcomponents.TextView_Black;
import com.jlabs.peepaid.customcomponents.TextView_White;
import com.jlabs.peepaid.eventInterface.FragmentsEventInitialiser;
import com.jlabs.peepaid.fragmento.DustbinFragment;
import com.jlabs.peepaid.fragmento.ToiletFragment;
import com.jlabs.peepaid.functions.JSONfunctions;
import com.jlabs.peepaid.functions.Static_Catelog;
import com.jlabs.peepaid.searchviewlay.SearchStaticRecyclerFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xyz.sahildave.widget.SearchViewLayout;


public class Main22Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,LocationListener {
    private static final int EMAIL_ACTIVITY_REQUEST = 1;
    private static final String TAG = Main22Activity.class.getSimpleName();
    private ViewPager mViewPager;
    public static String email;
    public static String firstname;
    public static String lastname;
    public static String pics;
    TextView_Black help;
    TextViewModernM username,emails;
    RoundedImageView pic;
    LocationManager locationManager;
    LocationListener locationListener;
    Context context;
    TextView_White filter;
    LinearLayout star;
    ProgressDialog mProgressDialog;
    FragmentsEventInitialiser toilet_event,dustbin_event;
   // private String TAG = Main22Activity.class.getSimpleName();
    String url ="http://maps.google.com/maps/api/geocode/json?address=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        filter=(TextView_White)findViewById(R.id.filter);
        username=(TextViewModernM)findViewById(R.id.username);
        emails=(TextViewModernM)findViewById(R.id.email_id);
        pic=(RoundedImageView)findViewById(R.id.pic);
        star=(LinearLayout)findViewById(R.id.star);
        context=this;

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        firstname = intent.getStringExtra("fname");
        lastname = intent.getStringExtra("lname");
        pics = intent.getStringExtra("images");
        Static_Catelog.setStringProperty(context,"emal",email);
        Picasso.with(context)
                .load(pics)
                .resize(80, 80)
                .centerCrop()
                .into(pic);

        emails.setText(email);
        username.setText(firstname+" "+lastname);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, FilterActivity.class);
                startActivity(myIntent);


            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(context, Redeem.class);
                startActivity(myIntent);


            }
        });
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Retrieving Current Location...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);
        locationListener = this;
        useCurrent();



        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);





        // Search Vala jhol



        final SearchViewLayout searchViewLayout = (SearchViewLayout) findViewById(R.id.search_view_container);

      //  searchViewLayout.setExpandedContentSupportFragment(this, new SearchStaticFragment());
        searchViewLayout.setExpandedContentSupportFragment(this, new SearchStaticRecyclerFragment());
        searchViewLayout.handleToolbarAnimation(toolbar);
        searchViewLayout.setCollapsedHint("Search a location");
        searchViewLayout.setExpandedHint("Start Typing");
//        searchViewLayout.setHint("Global Hint");

        ColorDrawable collapsed = new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary));
        ColorDrawable expanded = new ColorDrawable(ContextCompat.getColor(this, R.color.default_color_expanded));
        searchViewLayout.setTransitionDrawables(collapsed, expanded);
        searchViewLayout.setSearchListener(new SearchViewLayout.SearchListener() {
            @Override
            public void onFinished(String searchKeyword) {
                searchViewLayout.collapse();
                Snackbar.make(searchViewLayout, "Start Search for - " + searchKeyword, Snackbar.LENGTH_LONG).show();
            }
        });
        searchViewLayout.setOnToggleAnimationListener(new SearchViewLayout.OnToggleAnimationListener() {
            @Override
            public void onStart(boolean expanding) {

            }

            @Override
            public void onFinish(boolean expanded) { }
        });
        searchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: " + s + "," + start + "," + count + "," + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s + "," + start + "," + before + "," + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 180000, 10, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
//            String longitude = "" + loc.getLongitude();
            String latitude = "" + loc.getLatitude();

            Log.i("LOGAN","Logs"+latitude);

            //Image image1 = new Image();
            //image1.setLat(loc.getLatitude());
            //image1.setLo(loc.getLongitude());
            if(toilet_event!=null)
            toilet_event.updateLocation(loc);
            if(dustbin_event!=null)
            dustbin_event.updateLocation(loc);

            //editLocation.setText(s);
//            Intent intent = new Intent(context, ToiletFragment.class);
//            intent.putExtra("lat", latitude);
//            intent.putExtra("lon", longitude);
//           // intent.putExtra("type",type);
//            startActivity(intent);
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();
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
        if (checkSelfPermission(
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main22, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



               return super.onOptionsItemSelected(item);

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//
//            mViewPager.setCurrentItem(0);
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//            mViewPager.setCurrentItem(1);
//        } else if (id == R.id.nav_redeem) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//            Intent intent =new Intent(Main22Activity.this, EnvelopeActivity.class);
//            this.startActivityForResult(intent, EMAIL_ACTIVITY_REQUEST);
//
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ToiletFragment(), "Toilet");
        adapter.addFragment(new DustbinFragment(), "Dustbin");


        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    ToiletFragment toiletFragment = ToiletFragment.init(position);
                    toilet_event=toiletFragment;
                    return toiletFragment;
                case 1 :
                    DustbinFragment dustbinFragment = DustbinFragment.init(position);
                    dustbin_event=dustbinFragment;
                    return dustbinFragment;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EMAIL_ACTIVITY_REQUEST && resultCode == RESULT_OK) {

            String[] to = data.getStringArrayExtra(Intent.EXTRA_EMAIL);
            String subject = data.getStringExtra(Intent.EXTRA_SUBJECT);
            String msg = data.getStringExtra(Intent.EXTRA_TEXT);
            String email1 = "kashyap@gmail.cc";
            String numb = "9711940752";

            //API Call wake

            JSONObject finalJson = new JSONObject();
            try {
                finalJson.put("email", email1);
                finalJson.put("vendor_id", 1);
                finalJson.put("phone", numb);
                finalJson.put("subject", subject);
                finalJson.put("body", msg);


            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("tracer", "" + finalJson);
            new Feedback(finalJson).execute();



        }
    }
    private class Feedback extends AsyncTask<String,Void,Void>
    {
        JSONObject object;
        Feedback(JSONObject obj)
        {
            object=obj;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONObject obj= JSONfunctions.makenewHttpRequest(context, "http://lannister-api.elasticbeanstalk.com/tyrion/feedback", object);

            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            Toast.makeText(context, "FeedBack Submitted", Toast.LENGTH_LONG).show();
            // finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager != null)
        locationManager.removeUpdates(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        useCurrent();
    }
}
