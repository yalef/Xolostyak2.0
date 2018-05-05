package com.example.user.xolostyak20;

/**
 * Created by User on 03.04.2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 22.02.2018.
 */

class ResultViewAdapter extends RecyclerView.Adapter<ResultViewAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Recept> recepts;

    ResultViewAdapter(Context context, List<Recept> recepts) {
        this.recepts = recepts;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ResultViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewAdapter.ViewHolder holder, int position) {
        final Recept recept = recepts.get(position);
        Picasso.with(context).load(recept.getImage()).into(holder.imageView);
        holder.nameView.setText(recept.getName());
        holder.discrView.setText(recept.getIngridients());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReceptActivity.class);
                intent.putExtra("name",recept.getName());
                //intent.putExtra("disc",recept.getDiscription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recepts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, discrView;
        final CardView cv;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.name);
            discrView = (TextView) view.findViewById(R.id.discr);
            cv = (CardView) view.findViewById(R.id.cv);
        }
    }
}
/*    @Override
    public void onBindViewHolder(ResultViewAdapter.ViewHolder holder, int position) {
        final Recept recept = receptes.get(position);
        //holder.imageView.setImageResource(recept.getImage());
        holder.nameView.setText(recept.getName());
        holder.discriptionView.setText(recept.getDiscription());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReceptActivity.class);
                intent.putExtra("name",recept.getName());
                intent.putExtra("disc",recept.getDiscription());
                context.startActivity(intent);
            }
        });
    }*/