package com.jlabs.peepaid.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlabs.peepaid.R;
import com.jlabs.peepaid.customcomponents.ButtonModarno;
import com.jlabs.peepaid.customcomponents.TextViewModernM;
import com.jlabs.peepaid.maps.HttpConnection;
import com.jlabs.peepaid.maps.PathJSONParser;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends FragmentActivity implements LocationListener {



    double latte, longi;
    double lat, lng;


    LocationManager locationManager;
    LocationListener locationListener;
    Context context;
    double l1,l2;
    TextViewModernM  direction;
    ButtonModarno takeme;
    GoogleMap googleMap;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        context=this;
        Intent i=getIntent();
         lat=i.getDoubleExtra("lat",0.0);
         lng=i.getDoubleExtra("long",0.0);
        direction=(TextViewModernM)findViewById(R.id.directions);
        takeme=(ButtonModarno)findViewById(R.id.takeme);




        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Retrieving Current Location...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);
        locationListener = this;
        useCurrent();



        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        googleMap = fm.getMap();
        
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
         latte =  loc.getLatitude();
         longi = loc.getLongitude();
//        latte = loc.getLatitude();
//        longi = loc.getLongitude();

        Log.i("Longit"+longi,""+latte);

        setmarkers();

        //Image image1 = new Image();
        //image1.setLat(loc.getLatitude());
        //image1.setLo(loc.getLongitude());


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




    private String getMapsApiDirectionsUrl() {



 /*       String waypoints = "waypoints=optimize:true|"
                + LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude
                + "|" + "|" + BROOKLYN_BRIDGE.latitude + ","
                + BROOKLYN_BRIDGE.longitude + "|" + WALL_STREET.latitude + ","
                + WALL_STREET.longitude;
*/
        String waypoints = "origin="
                + latte + "," + longi
                + "&destination=" + lat + ","
                + lng;
        String sensor = "sensor=false";
        String params = waypoints + "&" + sensor;
        String output = "json";
        String url = "http://maps.googleapis.com/maps/api/directions/"
                + output + "?" + params;
        Location loc1 = new Location("");
        loc1.setLatitude(latte);
        loc1.setLongitude(longi);

        Location loc2 = new Location("");
        loc2.setLatitude(lat);
        loc2.setLongitude(lng);

        float distanceInMeters = loc1.distanceTo(loc2);

        direction.setText("DIRECTIONS ("+new DecimalFormat("##.#").format(distanceInMeters)+")m");



        return url;
    }



    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }

    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
                Log.i("New tag" ,"Routes "+routes.size());
                Log.i("New tag" ,"Routes "+" sagdsfj "+routes.get(0).size());
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("New tag" ,"error ");
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = new ArrayList<LatLng>();
//            PolylineOptions polyLineOptions = null;
//
//            // traversing through routes
//            for (int i = 0; i < routes.size(); i++) {
//                points = new ArrayList<LatLng>();
//                polyLineOptions = new PolylineOptions();
//                List<HashMap<String, String>> path = routes.get(i);
//
//                for (int j = 0; j < path.size(); j++) {
//                    HashMap<String, String> point = path.get(j);
//
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
//                }
//
//                polyLineOptions.addAll(points);
//                polyLineOptions.width(3);
//                polyLineOptions.color(Color.GREEN);
//            }
//
//            googleMap.addPolyline(polyLineOptions);
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


    public void setmarkers()
    {
        MarkerOptions options = new MarkerOptions();
        options.position( new LatLng(lat, lng));
       // options.position( new LatLng(latte, longi));

        googleMap.addMarker(options);
        String url = getMapsApiDirectionsUrl();
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),
                15));
        addMarkers();

    }

    private void addMarkers() {
        if (googleMap != null) {
//            googleMap.addMarker(new MarkerOptions().position(new LatLng(latte, longi))
//                    .title("You are here."));
            googleMap.addMarker(new MarkerOptions().position( new LatLng(lat, lng))
                    .title("Location"));
            takeme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + latte + "," + longi + "&daddr=" + lat + "," +lng));
                    startActivity(intent);
                }
            });

        }
    }
}