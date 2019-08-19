package com.enggemy22.mytrain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ViewHolder> {
    private Context context;
    private List<upload> list;

      //constractor
    public imageAdapter(Context context, List<upload> list) {
        this.context = context;
        this.list = list;
    }

      // LayoutInflator
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new ViewHolder(v);
    }

      //getPosition and show data in recycleView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        upload upload=list.get(position);
        holder.text.setText(upload.getTitle());
        Picasso.with(context).load(upload.getImage())
                .fit()
                .placeholder(R.mipmap.ic_launcher_round)
                .centerInside()
                .into(holder.imageView);

    }

     //getItem
    @Override
    public int getItemCount() {
        return list.size();
    }

     //inner class that identfiy data
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.txt_disc);
            imageView=itemView.findViewById(R.id.image_upload);

        }
    }

}
