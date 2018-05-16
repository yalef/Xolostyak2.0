package com.example.user.xolostyak20;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Recept> recepts;

    FavoriteAdapter(Context context, List<Recept> recepts ){
        this.recepts = recepts;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fav_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        final Recept recept = recepts.get(position);
        Picasso.with(context).load(recept.getImage()).into(holder.imageView);
        holder.nameView.setText(recept.getName());
        holder.discrView.setText(recept.getIngridients());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReceptActivity.class);
                intent.putExtra("name",recept.getName());
                intent.putExtra("disc",recept.getDiscription());
                intent.putExtra("pic",recept.getImage());
                intent.putExtra("ingr",recept.getIngridients());
                context.startActivity(intent);
            }
        });
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                rootref.child(user.getUid()).child(recept.getName()).removeValue();
                delete(recept);
                Snackbar.make(v, "Успешно удалено!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recepts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, discrView;
        final CardView cv;
        final ImageButton delete_btn;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_fav);
            nameView = (TextView) view.findViewById(R.id.name);
            discrView = (TextView) view.findViewById(R.id.discr);
            cv = (CardView) view.findViewById(R.id.cv_fav);
            delete_btn = (ImageButton) view.findViewById(R.id.delete_btn);
        }
    }

    private void delete(Recept recept) {
        int position = recepts.indexOf(recept);
        recepts.remove(position);
        notifyItemRemoved(position);
    }

}
