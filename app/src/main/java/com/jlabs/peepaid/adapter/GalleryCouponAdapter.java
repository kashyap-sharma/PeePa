package com.jlabs.peepaid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jlabs.peepaid.R;
import com.jlabs.peepaid.model.Image;

import java.util.List;

/**
 * Created by Kashyap on 31/03/16.
 */

public class GalleryCouponAdapter extends RecyclerView.Adapter<GalleryCouponAdapter.MyViewHolder> {

    private List<Image> images;
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public GalleryCouponAdapter(Context context, List<Image> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Image image = images.get(position);

        Glide.with(mContext).load(image.getMedium())
                .thumbnail(1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
//        holder.title.setText(image.getTitle());
//        holder.rat.setText(mContext.getResources().getString(R.string.rating)+""+image.getRating());
//        //double d=image.getDis();
//        //d =Double.parseDouble(new DecimalFormat("##.#").format(d));
//        //  holder.dist.setText(mContext.getResources().getString(R.string.locat)+" "+ String.format("%.02f", image.getDis()) + "km");
//        holder.dist.setText(mContext.getResources().getString(R.string.locat)+""+image.getDis() + " km");

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


}
