package com.jlabs.peepaid.fragmento;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jlabs.peepaid.R;
import com.jlabs.peepaid.adapter.GalleryCouponAdapter;
import com.jlabs.peepaid.app.AppController;
import com.jlabs.peepaid.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JLabs on 05/24/16.
 */
public class Coupons extends Fragment {

    private String TAG = Coupons.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<Image> images;
    private ArrayList<ArrayList<Image>> main_images;
    private ProgressDialog pDialog;
    private GalleryCouponAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayout nodata;

    //New For Loc

    //New For Loc
    Toolbar toolbar;
    public Coupons() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_for_toilet_and_dustbin, container, false);
        //  View v = getView();
        // toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        // ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.VISIBLE);
        nodata=(LinearLayout)v.findViewById(R.id.nodata);
        pDialog = new ProgressDialog(getActivity());
        images = new ArrayList<>();
        main_images = new ArrayList<>();
        mAdapter = new GalleryCouponAdapter(getActivity().getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("images", main_images.get(position));
//                bundle.putInt("position", position);
//
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
//                newFragment.setArguments(bundle);
//                newFragment.show(ft, "slideshow");
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        fetchImages();
        return v;
    }





    //New For Loc


    //New For Loc








//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        Log.v(TAG, "Initializing sounds...");
//
//
//
//        View v = getView();
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//       //getActivity().setSupportActionBar(toolbar);
//
//        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
//
//        pDialog = new ProgressDialog(getActivity());
//        images = new ArrayList<>();
//        mAdapter = new GalleryAdapter(getActivity().getApplicationContext(), images);
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
//
//        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("images", images);
//                bundle.putInt("position", position);
//
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
//                newFragment.setArguments(bundle);
//                newFragment.show(ft, "slideshow");
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//
//        fetchImages();
//
//
//
//    }


    private void fetchImages() {

        pDialog.setMessage("Downloading json...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(object.getString("name"));

                                JSONObject url = object.getJSONObject("url");
                                image.setSmall(url.getString("small"));
                                image.setMedium(url.getString("medium"));
                                image.setLarge(url.getString("large"));
                                image.setTimestamp(object.getString("timestamp"));

                                images.add(image);

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

}