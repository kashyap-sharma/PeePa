package com.jlabs.peepaid.fragmento;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jlabs.peepaid.R;
import com.jlabs.peepaid.adapter.GalleryMainAdapter;
import com.jlabs.peepaid.model.Image;

import java.util.ArrayList;

/**
 * Created by JLabs on 05/24/16.
 */
public class Recharges extends Fragment {

    private String TAG = Recharges.class.getSimpleName();
    private static final String endpoint = "http://lannister-api.elasticbeanstalk.com/jaime/search?p=0";
    private ArrayList<Image> images;
    private ArrayList<ArrayList<Image>> main_images;
    private ProgressDialog pDialog;
    private GalleryMainAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayout nodata;

    //New For Loc

    //New For Loc
    Toolbar toolbar;
    public Recharges() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.recharge_frag, container, false);

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


//    private void fetchImages() {
//        String tag_json_obj = "json_obj_req";
//        pDialog.setMessage("Downloading json...");
//        pDialog.show();
//        Image i=new Image();
//        Double m = i.getLat();
//        Double  n = i.getLo();
//        Log.i("addresss", "" + endpoint + "&lat=" + i.getLat() + "&lng=" + n + "&t=dustbin");
//        // &lat=29.5081149&lng=77.1975841&t=toilet
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, endpoint+"&lat="+m+"&lng="+n+"&t=dustbin", null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(final JSONObject response) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d(TAG, response.toString());
//                                pDialog.hide();
//
//                                images.clear();
//                                main_images.clear();
//
//                                try {
//                                    JSONArray object1 = response.getJSONArray("data");
//
//                                    for (int i = 0; i < object1.length(); i++) {
//
//                                        JSONObject ob = object1.getJSONObject(i);
//
//                                        JSONArray object = ob.getJSONArray("pics");
//
//                                        Image image = new Image();
//                                        image.setTitle(ob.getString("title"));
//                                        image.setReview(ob.getString("review"));
//                                        try {
//                                            image.setDis(ob.getDouble("distance"));
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        image.setRating(ob.getDouble("rating"));
//                                        image.setSmall(object.get(0).toString());
//                                        image.setMedium(object.get(0).toString());
//                                        image.setLarge(object.get(0).toString());
//                                        ArrayList<Image> tempImages = new ArrayList<Image>();
//                                        for (int k = 0; k < object.length(); k++) {
//                                            Image image1 = new Image();
//                                            image1.setSmall(object.get(k).toString());
//                                            image1.setMedium(object.get(k).toString());
//                                            image1.setLarge(object.get(k).toString());
//                                            tempImages.add(image1);
//                                        }
//                                        main_images.add(tempImages);
//
//                                        images.add(image);
//                                        Log.e(TAG, "android " + object.get(0).toString());
//
//
//                                    }
//
//                                } catch (JSONException e) {
//                                    Log.e(TAG, "Konvict" + "Json parsing error: " + e.getMessage());
//                                    nodata.setVisibility(View.VISIBLE);
//                                    recyclerView.setVisibility(View.GONE);
//                                }
//
//
//                                mAdapter.notifyDataSetChanged();
//
//                            }
//                        });
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Error: " + error.getMessage());
//                pDialog.hide();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(req,tag_json_obj);
//    }

}