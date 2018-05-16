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
TextView name_rec_txt;
TextView disc_rec_txt;
TextView ingr_rec_txt;

ImageView img, img_big;

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
        name_rec_txt = (TextView) findViewById(R.id.textView);
        disc_rec_txt = (TextView) findViewById(R.id.disc_rec);
        ingr_rec_txt = (TextView) findViewById(R.id.ingr_rec);
        tb = (Toolbar) findViewById(R.id.tb_rec);
        btn_fav = (Button) findViewById(R.id.btn_fav);
        img = (ImageView) findViewById(R.id.imageView);
        img_big = (ImageView) findViewById(R.id.imageView2);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final String name_rec = getIntent().getStringExtra("name");
        final String disc_rec = getIntent().getStringExtra("disc");
        final String ingr_rec = getIntent().getStringExtra("ingr");
        final String img_rec = getIntent().getStringExtra("pic");
        name_rec_txt.setText(name_rec);
        disc_rec_txt.setText(disc_rec);
        ingr_rec_txt.setText(ingr_rec);
        Picasso.with(ReceptActivity.this).load(img_rec).into(img);
        Picasso.with(ReceptActivity.this).load(img_rec).into(img_big);


        rootRef = FirebaseDatabase.getInstance().getReference();

        rec_list = new ArrayList<>();

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.child(user.getUid()).child(name_rec).setValue(name_rec.toString());
                Snackbar.make(vw, "Добавлено в избранное", Snackbar.LENGTH_LONG).show();
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
}
