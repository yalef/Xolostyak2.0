package com.example.user.xolostyak20;

import android.content.Intent;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    final List<Recept> recept_list = new ArrayList<>();
    List<String> ingr_list = new ArrayList<>();
    private DatabaseReference rootRef;
    String name_rec, disc_rec, ingrs_rec, image_rec,filter;
    RecyclerView rv;
    Toolbar tb;
    View v;
    int number_child, i;
    ArrayList<String> user_arr = new ArrayList<>();
    final ArrayList<String> test_arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        v = (View) findViewById(R.id.main_result);
        rv = (RecyclerView) findViewById(R.id.rv);
        tb = (Toolbar) findViewById(R.id.toolbar_result);

        setSupportActionBar(tb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

/*        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(),
                DividerItemDecoration.VERTICAL));*/

        final String result = getIntent().getStringExtra("select");
        filter = getIntent().getStringExtra("filter");
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        rootRef = FirebaseDatabase.getInstance().getReference();

        //Костыль для получения выбранных элементов
        try {
            String[] result_arr = result.split(",");
            for (int i = 0; i < result_arr.length; i++) {
                user_arr.add(result_arr[i]);
            }
        }catch (NullPointerException e){
            Snackbar.make(v, "Ничего не найдено", Snackbar.LENGTH_LONG).setAction("ok",snackbarOnClickListener).show();
        }

        DatabaseReference ingridientsRef = rootRef.child("List").child("Ingridients");
        switch (filter){
            case "strict":
                search_strict(ingridientsRef);
                //updateUI();
            case "free":
                search_free(ingridientsRef);
                //updateUI();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void search_free(final DatabaseReference ref) {
        ValueEventListener valueEventListener_second = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                number_child = ((int) dataSnapshot.getChildrenCount())-1;
                for (int i = 0; i <= number_child; i++) {
                    ref.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            test_arr.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String ingridient = ds.getKey();
                                test_arr.add(ingridient);
                                Log.d("TAG", ingridient);
                                if (!Collections.disjoint(user_arr, test_arr)) {
                                    //String id = String.valueOf(i-1);
                                    String id = dataSnapshot.getKey();
                                    if(filter.contains("free")){
                                        getRecInfo(id);
                                    }
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(valueEventListener_second);
    }

    public void search_strict(final DatabaseReference ref){
        ValueEventListener valueEventListener_second = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                number_child = ((int) dataSnapshot.getChildrenCount())-1;
                for (i = 0; i <= number_child; i++) {
                    ref.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            test_arr.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String ingridient = ds.getKey();
                                test_arr.add(ingridient);
                                Log.d("TAG", ingridient);
                                Collections.sort(user_arr);
                                Collections.sort(test_arr);
                            }
                            if (test_arr.equals(user_arr)) {
                                //String id = String.valueOf(i-1);
                                String id = dataSnapshot.getKey();
                                if(filter.contains("strict")){
                                    getRecInfo(id);
                                }
                                //break;
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addValueEventListener(valueEventListener_second);
        updateUI();
    }
    void getRecInfo(String id){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name_rec=dataSnapshot.child("Name").getValue(String.class);
                disc_rec=dataSnapshot.child("Discription").getValue(String.class);
                ingrs_rec=dataSnapshot.child("Ingridients").getValue(String.class);
                image_rec=dataSnapshot.child("pic").getValue(String.class);
                recept_list.add(new Recept(name_rec,disc_rec,ingrs_rec,image_rec));
                updateUI();
                if(recept_list.size()<0){
                    Snackbar.make(v, "Ничего не найдено", Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        rootRef.child("Recepts").child(id).addListenerForSingleValueEvent(valueEventListener);
    }
    void updateUI(){
        ResultViewAdapter adapter = new ResultViewAdapter(ResultActivity.this, recept_list);
        rv.setAdapter(adapter);
    }

    View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ResultActivity.this,SearchActivity.class);
            startActivity(i);
        }
    };

}
