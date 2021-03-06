package com.example.user.xolostyak20;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    Toolbar tb;
    RecyclerView rv;
    View v;

    String name_rec, ingr_rec, image_rec,disc_rec;

    List<String> list;
    List<Recept> recept_list;

    private DatabaseReference rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        v = (View) findViewById(R.id.favorite_activity);
        rv = (RecyclerView) findViewById(R.id.rv_fav);
        tb = (Toolbar) findViewById(R.id.tb_fav);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        rootref = FirebaseDatabase.getInstance().getReference();
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference fav_ref = rootref.child(user.getUid());
            fav_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String value = ds.getValue(String.class);
                        list.add(value);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (NullPointerException e){
            Snackbar.make(v, "Для использования этой функции вам необходимо авторизироваться.", Snackbar.LENGTH_LONG)
                    .setAction("Авторизация",snackbarOnClickListener).show();
        }

        Query fav_query = rootref.child("Recepts");
        recept_list = new ArrayList<>();
        fav_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String value = ds.child("Name").getValue(String.class);

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).contains(value)) {
                                name_rec = ds.child("Name").getValue(String.class);
                                ingr_rec = ds.child("Ingridients").getValue(String.class);
                                image_rec = ds.child("pic").getValue(String.class);
                                disc_rec = ds.child("Discription").getValue(String.class);
                                recept_list.add(new Recept(name_rec,disc_rec,ingr_rec,image_rec));
                                FavoriteAdapter adapter = new FavoriteAdapter(FavoriteActivity.this, recept_list);
                                rv.setAdapter(adapter);
                            }
                        }
                    }
                    //updateUI();
                } catch (NullPointerException e) {
                    Log.d("favor",e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(FavoriteActivity.this,SignInActivity.class);
            startActivity(i);
        }
    };

}
