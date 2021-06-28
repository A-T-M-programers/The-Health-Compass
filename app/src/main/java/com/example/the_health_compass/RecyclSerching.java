package com.example.the_health_compass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclSerching extends RecyclerView.Adapter<RecyclSerching.ExampleViewHolder> {
    private ArrayList<ListDoctor> Doctors;
    private ItemClickListener1 mItemListener;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_profile;
        public TextView name;
        public TextView phone;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            phone = itemView.findViewById(R.id.textView2);

        }
    }

    public RecyclSerching(ArrayList<ListDoctor> exampleList,ItemClickListener1 itemClickListener) {
        Doctors = exampleList;
        this.mItemListener = itemClickListener;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycl_serching,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ListDoctor currentItem = Doctors.get(position);
        //byte[] decode = Base64.decode(currentItem.getImageResource(),Base64.DEFAULT);
        //final Bitmap decodebitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);

        //holder.image_profile.setImageBitmap(decodebitmap);
        holder.name.setText(currentItem.getText1());
        //holder.phone.setText(currentItem.getText2());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(Doctors.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return Doctors.size();
    }

    public interface ItemClickListener1{
        void onItemClick(ListDoctor listDoctor);
    }

    public void filterList(ArrayList<ListDoctor> filteredList) {
        Doctors = filteredList;
        notifyDataSetChanged();
    }
}