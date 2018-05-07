package com.example.user.xolostyak20;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private DatabaseReference rootRef;
    String name_rec,disc_rec,ingrs_rec,image_rec;
    RecyclerView rv;
    Toolbar tb;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        v = (View)findViewById(R.id.main_result);
        rv = (RecyclerView) findViewById(R.id.rv);
        tb = (Toolbar) findViewById(R.id.toolbar_result);

        setSupportActionBar(tb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final String result = getIntent().getStringExtra("select");
        final List<Recept> recept_list = new ArrayList<>(); //Список рецептов для rv

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        rootRef = FirebaseDatabase.getInstance().getReference();
        Query result_query = rootRef.child("Recepts").orderByChild("Ingridients");

        result_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(result == null || result.length()<2){
                            Snackbar.make(v, "Nothing found", Snackbar.LENGTH_LONG)
                                    .setAction("ok", snackbarOnClickListener).show();
                            break;
                        }
                        String value = ds.child("Ingridients").getValue(String.class);
                        List<String> list = new ArrayList<>();
                        if (value.contains(result)) {
                            name_rec = ds.child("Name").getValue(String.class);
/*                            list.add(name_rec);
                            ingrs_rec = ds.child("Ingridients").getValue(String.class);*/
                            disc_rec = ds.child("Discription").getValue(String.class);
                            image_rec = ds.child("pic").getValue(String.class);
                            Recept recept = new Recept(name_rec,disc_rec,image_rec);
                            recept_list.add(recept);
                            ResultViewAdapter adapter = new ResultViewAdapter(ResultActivity.this, recept_list);
                            rv.setAdapter(adapter);
                        }
                    }
                }catch (NullPointerException e){
                    Snackbar.make(v, "Nothing found", Snackbar.LENGTH_LONG)
                            .setAction("ok", snackbarOnClickListener).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    /*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(requestCode == RESULT_OK){

            }
        }
    }*/

    View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ResultActivity.this,SearchActivity.class);
            //startActivityForResult(i,1);
            startActivity(i);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
