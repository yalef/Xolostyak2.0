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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReceptActivity extends AppCompatActivity {
View vw;
TextView tv;
ImageView img;

private DatabaseReference rootRef;
final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

Toolbar tb;
Button btn_fav;

List<String> rec_list;
public static String PREF_IS_CHECKED = "is_checked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        vw = (View) findViewById(R.id.recept_activity);
        tv = (TextView) findViewById(R.id.textView);
        tb = (Toolbar) findViewById(R.id.tb_rec);
        btn_fav = (Button) findViewById(R.id.btn_fav);
        img = (ImageView) findViewById(R.id.imageView);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final String name_rec = getIntent().getStringExtra("name");
        final String img_rec = getIntent().getStringExtra("pic");
        tv.setText(name_rec);
        Picasso.with(ReceptActivity.this).load(img_rec).into(img);
        rootRef = FirebaseDatabase.getInstance().getReference();

        rec_list = new ArrayList<>();

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.child(user.getUid()).child(name_rec).setValue(name_rec.toString());
                Snackbar.make(vw, "Added to favorite!", Snackbar.LENGTH_LONG).show();
/*                update_fire();
                test(rec_list,name_rec);*/
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
/*    public void test(List<String> rec_list,String name_rec) {
        if (rec_list.size() == 0) {
            rootRef.child(user.getUid()).child(name_rec).setValue(name_rec.toString());
            Snackbar.make(vw, "Added to favorite!", Snackbar.LENGTH_LONG).show();

        } else {
            for (int i = 0; i < rec_list.size(); i++) {

*//*                if (rec_list.get(i).contains(name_rec)) {
                    //удаление
                    rootRef.child(user.getUid()).child(name_rec).removeValue();
                    Snackbar.make(vw, "Removed from favorite!", Snackbar.LENGTH_LONG).show();

                    break;
                } if {*//*
                    //добавление
                    rootRef.child(user.getUid()).child(name_rec).setValue(name_rec.toString());
                    Snackbar.make(vw, "Added to favorite!", Snackbar.LENGTH_LONG).show();

                    break;
                    }
        }
    }

    public void update_fire(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rootRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    rec_list.clear();
                }
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String value = ds.getValue(String.class);
                    rec_list.add(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}
