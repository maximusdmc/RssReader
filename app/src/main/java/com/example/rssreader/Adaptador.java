package com.example.rssreader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    ArrayList<GenVariables> genVariables;
    Context context;

    public Adaptador(Context context, ArrayList<GenVariables> genVariables){
        this.genVariables = genVariables;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custum_row_news_item,
                parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.cardView);
        final GenVariables current= genVariables.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        Picasso.get().load(current.getThumbnailUrl()).into(holder.Thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,DetalleNota.class);
                i.putExtra("Title", current.getTitle());
                i.putExtra("Content", current.getContent());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return genVariables.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Description;
        ImageView Thumbnail;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title= itemView.findViewById(R.id.title_text);
            Description= itemView.findViewById(R.id.description_text);
            Thumbnail= itemView.findViewById(R.id.thumb_img);
            cardView= itemView.findViewById(R.id.cardview);
        }
    }

}
