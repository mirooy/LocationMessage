package com.example.cs160_sp18.prog3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import android.view.View;



public class recycleradapter extends RecyclerView.Adapter<recycleradapter.MyViewHolder> {
    private List<Place> places;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView landmark, coordinates, jpgname, rangebetweencurrent;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            landmark= view.findViewById(R.id.landmark_name);
            rangebetweencurrent = view.findViewById(R.id.coordinates);
            image = view.findViewById(R.id.image);

        }
    }

    public recycleradapter(List<Place> places) {
        this.places = places;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_model, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Place p = places.get(position);
        holder.landmark.setText(p.getLandmark());
        holder.rangebetweencurrent.setText(p.getDistance());
        holder.image.setImageResource(p.getimage());
    }
    @Override
    public int getItemCount() {
        return places.size();
    }

}
