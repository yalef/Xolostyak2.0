package com.example.user.xolostyak20;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ReceptActivity extends AppCompatActivity {
View v;
TextView tv;
RecyclerView rv;
private DatabaseReference rootRef;

Toolbar tb;
CheckBox fav;

List<String> ingr_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        rv = (RecyclerView) findViewById(R.id.rv_rec);
        v = (View) findViewById(R.id.recept_activity);
        tv = (TextView) findViewById(R.id.textView);
        tb = (Toolbar) findViewById(R.id.tb_rec);
        fav = (CheckBox) findViewById(R.id.fav);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final String name_rec = getIntent().getStringExtra("name");
        tv.setText(name_rec);

        String[] names = name_rec.split(",");
        for(int i =0;i<names.length;i++){
            ingr_list.add(names[i]);
        }

        rootRef = FirebaseDatabase.getInstance().getReference();

        fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    rootRef.child(user.getUid()).child("fav").push().setValue(name_rec.toString());
                    Snackbar.make(v, "Added to favorite!", Snackbar.LENGTH_LONG)
                            .show();
                }else{

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
