package com.jlabs.peepaid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jlabs.peepaid.R;
import com.jlabs.peepaid.customcomponents.TextViewModernM;
import com.jlabs.peepaid.customcomponents.TextView_Black;
import com.jlabs.peepaid.model.Image;

import java.util.List;

/**
 * Created by Lincoln on 31/03/16.
 */

public class GalleryMainAdapter extends RecyclerView.Adapter<GalleryMainAdapter.MyViewHolder> {

    private List<Image> images;
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        private TextViewModernM title;
        private TextView_Black dist, rat;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            title=(TextViewModernM)view.findViewById(R.id.text_location);
            dist=(TextView_Black)view.findViewById(R.id.dist);
            rat=(TextView_Black)view.findViewById(R.id.rat);
        }
    }


    public GalleryMainAdapter(Context context, List<Image> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);

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
        holder.title.setText(image.getTitle());
        holder.rat.setText(mContext.getResources().getString(R.string.rating)+""+image.getRating());
        //double d=image.getDis();
        //d =Double.parseDouble(new DecimalFormat("##.#").format(d));
      //  holder.dist.setText(mContext.getResources().getString(R.string.locat)+" "+ String.format("%.02f", image.getDis()) + "km");
        holder.dist.setText(mContext.getResources().getString(R.string.locat)+""+image.getDis() + " km");

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryMainAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryMainAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}